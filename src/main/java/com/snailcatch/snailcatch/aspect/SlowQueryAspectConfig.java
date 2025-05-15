package com.snailcatch.snailcatch.aspect;

import com.snailcatch.snailcatch.config.SlowQueryProperties;
import com.snailcatch.snailcatch.util.SlowQueryHolder;
import com.snailcatch.snailcatch.util.SlowQueryLogger;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlowQueryProperties.class)
public class SlowQueryAspectConfig {

    private static final long SLOW_QUERY_THRESHOLD_MS = 500;

    @Bean
    public Advisor slowQueryAdvisor(SlowQueryProperties properties, SlowQueryLogger logger) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(properties.getRepositoryPointcut());

        return new DefaultPointcutAdvisor(pointcut, (MethodInterceptor) invocation -> {
            long start = System.currentTimeMillis();
            Object result = invocation.proceed();
            long duration = System.currentTimeMillis() - start;

            if (duration >= SLOW_QUERY_THRESHOLD_MS) {
                String method = invocation.getMethod().toGenericString();
                String sql = SlowQueryHolder.getCurrentQuery();
                String explain = logger.explain(sql);
                logger.logSlowQuery(method, sql, duration, explain);
            }

            return result;
        });
    }
}
