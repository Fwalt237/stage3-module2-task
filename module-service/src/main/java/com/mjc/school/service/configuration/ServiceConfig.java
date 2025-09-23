package com.mjc.school.service.configuration;

import com.mjc.school.repository.configuration.RepositoryConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.mjc.school.service")
@Import({RepositoryConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServiceConfig {
}
