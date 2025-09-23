package com.mjc.school.cli;

import com.mjc.school.command.interfaces.Command;
import com.mjc.school.command.interfaces.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CommandLineInterface {
    private final CommandExecutor commandExecutor;
    private final Scanner scanner;

    @Autowired
    public CommandLineInterface(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        while(true){
            printMenu();
            String input = scanner.nextLine().trim();

            if(input.equals("0")){
                break;
            }

            try{
                Map<String, Object> parameters = collectParameters(input);
                commandExecutor.executeCommand(input, parameters);
            }catch (Exception e){
                System.err.println("Error: "+e.getMessage());
            }
        }
    }

    private void printMenu(){
        System.out.println("Enter the number of operation:");
        List<Command> commands = commandExecutor.getAllCommands();
        commands.stream()
                .sorted((c1,c2) -> c1.getCommandId().compareTo(c2.getCommandId()))
                .forEach(c -> System.out.printf("%s - %s%n", c.getCommandId(),c.getDescription()));
        System.out.println("0 - Exit");
    }

    private Map<String, Object> collectParameters(String commandId){
        Map<String, Object> parameters = new HashMap<>();

        switch (commandId){
            case "1" -> System.out.println("Operation: Get all news.");
            case "6" -> System.out.println("Operation: Get all authors.");
            case "2" -> {
                System.out.println("Operation: Get news by id.");
                System.out.println("Enter news id:");
                parameters.put("newsId", Long.parseLong(scanner.nextLine().trim()));
            }
            case "7" -> {
                System.out.println("Operation: Get author by id.");
                System.out.println("Enter author id:");
                parameters.put("authorId", Long.parseLong(scanner.nextLine().trim()));
            }
            case "5" -> {
                System.out.println("Operation: Remove news by id.");
                System.out.println("Enter news id:");
                parameters.put("newsId", Long.parseLong(scanner.nextLine().trim()));
            }
            case "10" -> {
                System.out.println("Operation: Remove author by id.");
                System.out.println("Enter author id:");
                parameters.put("authorId", Long.parseLong(scanner.nextLine().trim()));
            }
            case "3" -> {
                System.out.println("Operation: Create news.");
                System.out.println("Enter news title:");
                String title = scanner.nextLine().trim();
                System.out.println("Enter news content:");
                String content = scanner.nextLine().trim();
                System.out.println("Enter author id:");
                Long authorId = Long.parseLong(scanner.nextLine().trim());
                parameters.put("title", title);
                parameters.put("content", content);
                parameters.put("authorId", authorId);
            }
            case "8" -> {
                System.out.println("Operation: Create author.");
                System.out.println("Enter author name:");
                parameters.put("name", scanner.nextLine().trim());
            }

            case "4" -> {
                System.out.println("Operation: Update news.");
                System.out.println("Enter news id:");
                Long newsId = Long.parseLong(scanner.nextLine().trim());
                System.out.println("Enter news title: ");
                String title = scanner.nextLine().trim();
                System.out.println("Enter news content:");
                String content = scanner.nextLine().trim();
                System.out.println("Enter author id:");
                Long authorId = Long.parseLong(scanner.nextLine().trim());
                parameters.put("newsId", newsId);
                parameters.put("title", title);
                parameters.put("content", content);
                parameters.put("authorId", authorId);
            }

            case "9" -> {
                System.out.println("Operation: Update author.");
                System.out.print("Enter author id: ");
                Long authorId = Long.parseLong(scanner.nextLine().trim());
                System.out.print("Enter author name: ");
                String name = scanner.nextLine().trim();
                parameters.put("authorId", authorId);
                parameters.put("name", name);
            }
        }
        return parameters;
    }
}
