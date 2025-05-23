package com.snailcatch.snailcatch.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import com.p6spy.engine.spy.P6SpyDriver;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@ConditionalOnClass(P6SpyDriver.class)
@ConditionalOnProperty(prefix = "snailcatch.p6spy", name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6SpyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
                .driverClassName("com.p6spy.engine.spy.P6SpyDriver")
                .url(properties.getUrl().replace("jdbc:mysql", "jdbc:p6spy:mysql"))
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}
