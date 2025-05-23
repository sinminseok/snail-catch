package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.collector.QueryCollector;
import com.snailcatch.snailcatch.collector.QueryCollectorHolder;
import com.snailcatch.snailcatch.collector.impl.ThreadLocalQueryCollector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RepositoryProperties.class)
@ComponentScan(basePackages = "com.snailcatch.snailcatch")
public class SlowQueryAutoConfiguration {

    @Bean
    public QueryCollector slowQueryCollector() {
        ThreadLocalQueryCollector collector = new ThreadLocalQueryCollector();
        QueryCollectorHolder.setCollector(collector);
        return collector;
    }

}