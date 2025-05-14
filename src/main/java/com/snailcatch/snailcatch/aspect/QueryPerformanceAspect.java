package com.snailcatch.snailcatch.aspect;

import com.snailcatch.snailcatch.util.SlowQueryHolder;
import com.snailcatch.snailcatch.util.SlowQueryLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryPerformanceAspect {

    private static final long SLOW_QUERY_THRESHOLD_MS = 500; // 슬로우 쿼리 기준 시간

    private final SlowQueryLogger slowQueryLogger;

    public QueryPerformanceAspect(SlowQueryLogger slowQueryLogger) {
        this.slowQueryLogger = slowQueryLogger;
    }

    @Around("execution(* com.example..*Repository.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 쿼리 실행

        long duration = System.currentTimeMillis() - start;

        if (duration >= SLOW_QUERY_THRESHOLD_MS) {
            String methodName = joinPoint.getSignature().toShortString();
            String sql = SlowQueryHolder.getCurrentQuery(); // Hibernate Interceptor를 통해 SQL 추출
            String explain = slowQueryLogger.explain(sql);

            slowQueryLogger.logSlowQuery(methodName, sql, duration, explain);
        }

        return result;
    }
}
