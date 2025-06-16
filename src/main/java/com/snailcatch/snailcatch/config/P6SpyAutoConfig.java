package com.snailcatch.snailcatch.config;

import com.p6spy.engine.spy.P6SpyDriver;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.snailcatch.snailcatch.global.constants.AppConstants.PROPERTY_PREFIX;
import static com.snailcatch.snailcatch.global.constants.DataSourceConstants.*;

/**
 * Auto-configuration class for integrating P6Spy into Spring Boot applications.
 * <p>
 * This configuration will activate only if:
 * 1. The P6SpyDriver class is present in the classpath.
 * 2. The property "snail-catch.enabled" is set to true or is missing (defaults to true).
 * <p>
 * The main purpose is to wrap the original DataSource with P6Spy's proxy driver
 * to intercept and log all JDBC queries for easier SQL logging and debugging.
 */
@Configuration
@ConditionalOnClass(P6SpyDriver.class)
@ConditionalOnProperty(prefix = PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6SpyAutoConfig {

    /**
     * Defines a DataSource bean wrapped by P6SpyDriver.
     * <p>
     * This replaces the normal JDBC driver class name with "com.p6spy.engine.spy.P6SpyDriver",
     * and rewrites the JDBC URL to use P6Spy's proxy prefix ("jdbc:p6spy:mysql" instead of "jdbc:mysql").
     *
     * @param properties the original DataSourceProperties (configured via application.yml/properties)
     * @return a DataSource wrapped with P6Spy for query logging
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
                .driverClassName(P6SPY_DRIVER_CLASS_NAME)  // Use P6Spy driver to intercept SQL
                .url(properties.getUrl().replace(MYSQL_JDBC_PREFIX, P6SPY_JDBC_PREFIX))  // Wrap original JDBC URL
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}
