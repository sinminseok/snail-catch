package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.collector.SlowQueryCollector;
import com.snailcatch.snailcatch.collector.SlowQueryCollectorHolder;
import com.snailcatch.snailcatch.collector.impl.ThreadLocalSlowQueryCollector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlowQueryProperties.class)
@ComponentScan(basePackages = "com.snailcatch.snailcatch")
public class SlowQueryAutoConfiguration {

    @Bean
    public SlowQueryCollector slowQueryCollector() {
        ThreadLocalSlowQueryCollector collector = new ThreadLocalSlowQueryCollector();
        SlowQueryCollectorHolder.setCollector(collector);
        return collector;
    }

}