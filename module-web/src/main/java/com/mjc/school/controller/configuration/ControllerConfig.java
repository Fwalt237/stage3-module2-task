package com.mjc.school.controller.configuration;

import com.mjc.school.service.configuration.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.mjc.school.controller")
@Import({ServiceConfig.class})
public class ControllerConfig {
}
