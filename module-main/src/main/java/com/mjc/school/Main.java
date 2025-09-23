package com.mjc.school;

import com.mjc.school.cli.CommandLineInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
       ApplicationContext context = new AnnotationConfigApplicationContext(com.mjc.school.configuration.AppConfig.class);

       CommandLineInterface cli = context.getBean(CommandLineInterface.class);
       cli.start();
    }
}
