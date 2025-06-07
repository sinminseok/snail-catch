package com.snailcatch.snailcatch.global.aop.aspect;

import com.snailcatch.snailcatch.global.aop.interceptor.SnailCatchInterceptor;
import com.snailcatch.snailcatch.global.collector.QueryCollector;
import com.snailcatch.snailcatch.config.SnailCatchProperties;
import com.snailcatch.snailcatch.domain.query_log.repository.InMemoryQueryExecutionLogRepository;
import com.snailcatch.snailcatch.global.formatter.ExecutionPlanFormatter;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SnailCatchAspect {

    private final SnailCatchProperties properties;
    private final DataSource dataSource;
    private final ExecutionPlanFormatter executionPlanLogger;
    private final QueryCollector queryCollector;
    private final InMemoryQueryExecutionLogRepository inMemoryQueryExecutionLogRepository;


    public SnailCatchAspect(SnailCatchProperties properties, DataSource dataSource, QueryCollector queryCollector, ExecutionPlanFormatter executionPlanLogger, InMemoryQueryExecutionLogRepository inMemoryQueryExecutionLogRepository) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.executionPlanLogger = executionPlanLogger;
        this.queryCollector = queryCollector;
        this.inMemoryQueryExecutionLogRepository = inMemoryQueryExecutionLogRepository;
    }

    @Bean
    public Advisor slowQueryAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getRepositoryPointcut());
        MethodInterceptor interceptor = new SnailCatchInterceptor(queryCollector, executionPlanLogger, dataSource, inMemoryQueryExecutionLogRepository);
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}