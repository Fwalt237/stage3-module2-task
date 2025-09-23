package com.mjc.school.configuration;


import com.mjc.school.controller.configuration.ControllerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.mjc.school")
@Import({ControllerConfig.class})
public class AppConfig {
}
