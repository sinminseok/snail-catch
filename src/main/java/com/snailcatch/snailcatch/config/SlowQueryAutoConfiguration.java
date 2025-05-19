package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlowQueryProperties.class)
@ComponentScan(basePackages = "com.snailcatch.snailcatch")
public class SlowQueryAutoConfiguration {
}