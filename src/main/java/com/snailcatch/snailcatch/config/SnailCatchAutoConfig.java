package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.global.collector.QueryCollector;
import com.snailcatch.snailcatch.global.collector.QueryCollectorHolder;
import com.snailcatch.snailcatch.global.collector.impl.ThreadLocalQueryCollector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SnailCatchProperties.class)
@ComponentScan(basePackages = "com.snailcatch.snailcatch")
public class SnailCatchAutoConfig {
    @Bean
    public QueryCollector slowQueryCollector() {
        ThreadLocalQueryCollector collector = new ThreadLocalQueryCollector();
        QueryCollectorHolder.setCollector(collector);
        return collector;
    }
}