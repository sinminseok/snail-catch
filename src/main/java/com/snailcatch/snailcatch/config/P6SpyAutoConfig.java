package com.snailcatch.snailcatch.config;

import com.p6spy.engine.spy.P6SpyDriver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnClass(P6SpyDriver.class)
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = "snail-catch.p6spy", name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6SpyAutoConfig {

    @Bean
    @Primary
    public DataSource p6spyDataSource(DataSource originalDataSource, DataSourceProperties properties) {
        String url = properties.getUrl();
        if (url != null && url.startsWith("jdbc:mysql:")) {
            String p6spyUrl = url.replace("jdbc:mysql:", "jdbc:p6spy:mysql:");
            System.out.println("[SnailCatch] Converting jdbc:mysql → jdbc:p6spy:mysql: " + p6spyUrl);
            return DataSourceBuilder.create()
                    .driverClassName("com.p6spy.engine.spy.P6SpyDriver")
                    .url(p6spyUrl)
                    .username(properties.getUsername())
                    .password(properties.getPassword())
                    .build();
        }

        System.out.println("[SnailCatch] Skipping P6Spy wrapping – URL: " + url);
        return originalDataSource; // fallback to original if not mysql or already wrapped
    }
}