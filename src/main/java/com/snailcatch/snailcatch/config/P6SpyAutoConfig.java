package com.snailcatch.snailcatch.config;

import com.p6spy.engine.spy.P6SpyDriver;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Auto-configuration class for enabling P6Spy when it's available on the classpath
 * and explicitly enabled via configuration.
 *
 * <p>
 * This replaces the default {@link DataSource} with one wrapped by P6Spy
 * to allow SQL query logging.
 * </p>
 *
 * <p>
 * Conditions:
 * <ul>
 *     <li>{@link P6SpyDriver} must be present on the classpath</li>
 *     <li>Property <code>snailcatch.p6spy.enabled</code> must be true (default is true)</li>
 *     <li>No other {@link DataSource} bean must be defined</li>
 * </ul>
 * </p>
 */
@Configuration
@ConditionalOnClass(P6SpyDriver.class)
@ConditionalOnProperty(prefix = "snail-catch", name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6SpyAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        System.out.println("test start datasource");
        return DataSourceBuilder.create()
                .driverClassName("com.p6spy.engine.spy.P6SpyDriver")
                .url(properties.getUrl().replace("jdbc:mysql", "jdbc:p6spy:mysql"))
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}