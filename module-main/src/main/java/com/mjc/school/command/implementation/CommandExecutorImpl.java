package com.mjc.school.command.implementation;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.command.interfaces.Command;
import com.mjc.school.command.interfaces.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class CommandExecutorImpl implements CommandExecutor {

    private final Map<String, Command> commands = new HashMap<>();
    private final ApplicationContext applicationContext;
    private volatile boolean initialized = false;

    @Autowired
    public CommandExecutorImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){}

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!initialized){
            initializeCommands();
            initialized = true;
        }
    }

    private synchronized void initializeCommands(){
        if (initialized) {
            return;
        }
        Map<String, Object> beans = applicationContext
                .getBeansWithAnnotation(org.springframework.stereotype.Component.class);

        for (Object bean : beans.values()) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(CommandHandler.class)){
                    CommandHandler commandHandler = method.getAnnotation(CommandHandler.class);
                    String commandId = commandHandler.value();

                    Command command = new CommandImpl(commandId, method, bean, commandDescription(commandId));
                    commands.put(commandId, command);
                }

            }
        }
    }

    @Override
    public Optional<Command> findCommand(String commandId) {
        if (!initialized) {
            initializeCommands();
        }
        return Optional.ofNullable(commands.get(commandId));
    }

    @Override
    public void executeCommand(String commandId, Map<String, Object> parameters) {
        if (!initialized) {
            initializeCommands();
        }
        Command command = commands.get(commandId);
        if(command != null){
            try{
                Object result = command.execute(parameters);
                System.out.println(result);
            } catch (Exception e){
                System.err.println("Error executing command " + commandId + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Command not found.");
        }
    }

    @Override
    public List<Command> getAllCommands() {
        if (!initialized) {
            initializeCommands();
        }
        return new ArrayList<>(commands.values());
    }

    public String commandDescription(String commandId) {
        return switch (commandId) {
            case "1" -> "Get all news.";
            case "2" -> "Get news by id.";
            case "3" -> "Create  news.";
            case "4" -> "Update news.";
            case "5" -> "Remove news by id.";
            case "6" -> "Get all authors.";
            case "7" -> "Get author by id.";
            case "8" -> "Create author";
            case "9" -> "Update author";
            case "10" -> "Remove author by id.";
            default -> null;
        };

    }
}
