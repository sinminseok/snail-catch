package com.snailcatch.snailcatch.aop.aspect;

import com.snailcatch.snailcatch.aop.interceptor.SlowQueryInterceptor;
import com.snailcatch.snailcatch.collector.SlowQueryCollector;
import com.snailcatch.snailcatch.config.SlowQueryProperties;
import com.snailcatch.snailcatch.log.ExecutionPlanLogger;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class SlowQueryAspect {

    private final SlowQueryProperties properties;
    private final DataSource dataSource;
    private final ExecutionPlanLogger executionPlanLogger;
    private final SlowQueryCollector queryCollector;


    public SlowQueryAspect(SlowQueryProperties properties, DataSource dataSource, SlowQueryCollector queryCollector, ExecutionPlanLogger executionPlanLogger) {
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