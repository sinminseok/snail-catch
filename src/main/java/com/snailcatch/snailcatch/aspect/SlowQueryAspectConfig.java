package com.snailcatch.snailcatch.aspect;

import com.snailcatch.snailcatch.config.SlowQueryProperties;
import com.snailcatch.snailcatch.util.SlowQueryHolder;
import com.snailcatch.snailcatch.util.SlowQueryLogger;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlowQueryAspectConfig {

    private final SlowQueryLogger logger;
    private final SlowQueryProperties properties;

    public SlowQueryAspectConfig(SlowQueryProperties properties, SlowQueryLogger logger) {
        this.properties = properties;
        this.logger = logger;
    }

    @Bean
    public Advisor slowQueryAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getRepositoryPointcut());

        MethodInterceptor interceptor = new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                long start = System.currentTimeMillis();
                Object result = invocation.proceed();
                long duration = System.currentTimeMillis() - start;

                if (duration >= properties.getThresholdMillis()) {
                    String method = invocation.getMethod().toString();
                    String sql = SlowQueryHolder.getCurrentQuery();
                    String explain = logger.explain(sql);
                    logger.logSlowQuery(method, sql, duration, explain);
                }
                return result;
            }
        };

        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}