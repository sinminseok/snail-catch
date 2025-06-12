package com.snailcatch.snailcatch.config;

import com.p6spy.engine.spy.P6SpyDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnExpression("${snail-catch.enabled:true}")
public class P6SpyAutoConfig {

    /**
     * Creates a {@link DataSource} using the P6Spy driver, if one is not already defined.
     *
     * @param properties injected {@link DataSourceProperties} from Spring Boot
     * @return a P6Spy-wrapped {@link DataSource}
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
                .driverClassName("com.p6spy.engine.spy.P6SpyDriver")
                .url(replaceWithP6SpyUrl(properties.getUrl()))
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

    /**
     * Replaces the JDBC URL to use P6Spy instead of default MySQL driver.
     * For example: jdbc:mysql://... → jdbc:p6spy:mysql://...
     *
     * @param originalUrl the original JDBC URL
     * @return the transformed JDBC URL compatible with P6Spy
     */
    private String replaceWithP6SpyUrl(String originalUrl) {
        if (originalUrl == null) return null;
        return originalUrl.replace("jdbc:mysql", "jdbc:p6spy:mysql");
    }
}
