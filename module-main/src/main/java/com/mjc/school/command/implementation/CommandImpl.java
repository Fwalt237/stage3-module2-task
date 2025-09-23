package com.mjc.school.command.implementation;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.command.interfaces.Command;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class CommandImpl implements Command {
    private final String commandId;
    private final Method method;
    private final Object target;
    private final String commandDescription;

    public CommandImpl(String commandId, Method method, Object target, String commandDescription) {
        this.commandId = commandId;
        this.method = method;
        this.target = target;
        this.commandDescription = commandDescription;
        this.method.setAccessible(true);
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public Object execute(Map<String, Object> parameters) {
        try {
            if (parameters.isEmpty()) {
                return method.invoke(target);
            }
            Object[] methodParameters = resolveMethodParameters(parameters);
            return method.invoke(target, methodParameters);
        } catch (InvocationTargetException ite) {
            throw new RuntimeException("Error executing command: " +
                    ite.getTargetException().getMessage(), ite.getTargetException());
        } catch (Exception e) {
            throw new RuntimeException("Error invoking command method: "
                    + e.getMessage(), e);
        }
    }

    public Object[] resolveMethodParameters(Map<String, Object> parameters) {
        Parameter[] methodParameters = method.getParameters();
        Object[] args = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            Parameter parameter = methodParameters[i];

            if (parameter.isAnnotationPresent(CommandParam.class)) {
                CommandParam commandParam = parameter.getAnnotation(CommandParam.class);
                String paramName = commandParam.value();
                args[i] = parameters.get(paramName);
                args[i] = convertToExpectedType(args[i], parameter.getType());
            } else if (parameter.isAnnotationPresent(CommandBody.class)) {
                args[i] = buildCommandBody(parameters, parameter.getType());
            } else {
                String paramName = parameter.getName();
                args[i] = parameters.getOrDefault(paramName, null);
                args[i] = convertToExpectedType(args[i], parameter.getType());
            }
        }
        return args;
    }

    private Object convertToExpectedType(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (targetType.isInstance(value)) {
            return value;
        }

        if (targetType == Long.class || targetType == long.class) {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            try {
                return Long.parseLong(value.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert '" + value + "' to Long");
            }
        } else if (targetType == Integer.class || targetType == int.class) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert '" + value + "' to Integer");
            }
        } else if (targetType == String.class) {
            return value.toString();
        }
        return value;
    }

    private Object buildCommandBody(Map<String, Object> parameters, Class<?> targetType) {
//        try {
//            Object instance = targetType.getDeclaredConstructor().newInstance();
//
//            if (targetType == com.mjc.school.service.dto.NewsDtoRequest.class) {
//                Long id = (Long) parameters.get("newsId");
//                String title = (String) parameters.get("title");
//                String content = (String) parameters.get("content");
//                Long authorId = (Long) parameters.get("authorId");
//
//                var constructor = targetType.getDeclaredConstructor(Long.class, String.class, String.class, Long.class);
//                return constructor.newInstance(id, title, content, authorId);
//            }
//
//            if (targetType == com.mjc.school.service.dto.AuthorDtoRequest.class) {
//                Long id = (Long) parameters.get("authorId");
//                String name = (String) parameters.get("name");
//
//                var constructor = targetType.getConstructor(Long.class, String.class);
//                return constructor.newInstance(id, name);
//            }
//            return instance;

        try {
            if (targetType == NewsDtoRequest.class) {
                Long id = (Long) parameters.get("newsId"); // May be null for create
                String title = (String) parameters.get("title");
                String content = (String) parameters.get("content");
                Long authorId = (Long) parameters.get("authorId");
                return new NewsDtoRequest(id, title, content, authorId);
            } else if (targetType == AuthorDtoRequest.class) {
                Long id = (Long) parameters.get("authorId"); // May be null for create
                String name = (String) parameters.get("name");
                return new AuthorDtoRequest(id, name);
            }
            throw new IllegalArgumentException("Unsupported command body type: " + targetType.getSimpleName());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error instantiating command body: " + targetType.getSimpleName(), e);
        }
    }

    @Override
    public String getDescription() {
        return commandDescription;
    }
}
