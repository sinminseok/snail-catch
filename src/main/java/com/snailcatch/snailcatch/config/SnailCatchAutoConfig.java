package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.domain.query_log.collector.QueryCollector;
import com.snailcatch.snailcatch.domain.query_log.collector.QueryCollectorHolder;
import com.snailcatch.snailcatch.domain.query_log.collector.impl.ThreadLocalQueryCollector;
import com.snailcatch.snailcatch.domain.query_log.repository.InMemoryQueryExecutionLogRepository;
import com.snailcatch.snailcatch.global.aop.SnailCatchInterceptor;
import com.snailcatch.snailcatch.global.formatter.ExecutionPlanFormatter;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Auto-configuration class for SnailCatch.
 *
 * <p>
 * This configuration sets up the core beans necessary for SnailCatch to intercept
 * slow queries using AOP (Aspect-Oriented Programming) and collect query execution logs.
 * It will only be activated when the property 'snail-catch.enabled' is true or missing (defaults to true).
 * </p>
 *
 * <p>
 * The configuration includes:
 * <ul>
 *   <li>Binding of SnailCatchProperties for externalized configuration.</li>
 *   <li>Component scanning within the 'com.snailcatch.snailcatch' package.</li>
 *   <li>Registration of a ThreadLocal-based QueryCollector for capturing slow query data.</li>
 *   <li>Creation of an AOP Advisor that intercepts repository methods defined by the configured pointcut expression.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableConfigurationProperties(SnailCatchProperties.class)
@ComponentScan(basePackages = "com.snailcatch.snailcatch")
@ConditionalOnProperty(prefix = "snail-catch", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SnailCatchAutoConfig {

    private final SnailCatchProperties properties;

    /**
     * Constructor to inject SnailCatch configuration properties.
     *
     * @param properties the SnailCatchProperties instance bound to external configuration
     */
    public SnailCatchAutoConfig(SnailCatchProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates and registers a ThreadLocal-based QueryCollector bean.
     *
     * <p>
     * The QueryCollector collects information about slow queries during execution.
     * The created collector is also stored globally in QueryCollectorHolder for easy access.
     * </p>
     *
     * @return a new instance of ThreadLocalQueryCollector
     */
    @Bean
    public QueryCollector slowQueryCollector() {
        ThreadLocalQueryCollector collector = new ThreadLocalQueryCollector();
        QueryCollectorHolder.setCollector(collector);
        return collector;
    }

    /**
     * Creates an Advisor bean that applies SnailCatchInterceptor to methods
     * matched by the repository pointcut expression configured in SnailCatchProperties.
     *
     * <p>
     * This Advisor enables AOP interception to log slow queries for repository methods.
     * It uses dependencies such as DataSource, QueryCollector, ExecutionPlanFormatter,
     * and InMemoryQueryExecutionLogRepository to perform its functions.
     * </p>
     *
     * @param dataSource the DataSource used for database connections
     * @param queryCollector the QueryCollector bean for collecting query logs
     * @param executionPlanFormatter the formatter for database execution plans
     * @param logRepository in-memory repository for storing query execution logs
     * @return an Advisor configured with SnailCatchInterceptor and the appropriate pointcut
     */
    @Bean
    public Advisor slowQueryAdvisor(
            DataSource dataSource,
            QueryCollector queryCollector,
            ExecutionPlanFormatter executionPlanFormatter,
            InMemoryQueryExecutionLogRepository logRepository
    ) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getRepositoryPointcut());

        MethodInterceptor interceptor = new SnailCatchInterceptor(
                queryCollector, executionPlanFormatter, dataSource, logRepository
        );

        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
