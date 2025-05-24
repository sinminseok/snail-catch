package com.snailcatch.snailcatch.aop.aspect;

import com.snailcatch.snailcatch.aop.interceptor.SlowQueryInterceptor;
import com.snailcatch.snailcatch.collector.QueryCollector;
import com.snailcatch.snailcatch.config.RepositoryProperties;
import com.snailcatch.snailcatch.formatter.ExecutionPlanFormatter;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SlowQueryAspect {

    private final RepositoryProperties properties;
    private final DataSource dataSource;
    private final ExecutionPlanFormatter executionPlanLogger;
    private final QueryCollector queryCollector;


    public SlowQueryAspect(RepositoryProperties properties, DataSource dataSource, QueryCollector queryCollector, ExecutionPlanFormatter executionPlanLogger) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.executionPlanLogger = executionPlanLogger;
        this.queryCollector = queryCollector;
    }

    @Bean
    public Advisor slowQueryAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getRepositoryPointcut());
        MethodInterceptor interceptor = new SlowQueryInterceptor(queryCollector, executionPlanLogger, dataSource);
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}