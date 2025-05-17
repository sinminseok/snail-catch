//package com.snailcatch.snailcatch.aspect;
//
//import com.snailcatch.snailcatch.config.SlowQueryProperties;
//import com.snailcatch.snailcatch.util.SlowQueryHolder;
//import com.snailcatch.snailcatch.util.SlowQueryLogger;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//
//public class SlowQueryAspect implements MethodInterceptor {
//
//    private final SlowQueryLogger logger;
//    private final SlowQueryProperties properties;
//
//    public SlowQueryAspect(SlowQueryLogger logger, SlowQueryProperties properties) {
//        this.logger = logger;
//        this.properties = properties;
//    }
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object result = invocation.proceed();
//        long duration = System.currentTimeMillis() - start;
//        if (duration >= properties.getThresholdMillis()) {
//            String method = invocation.getMethod().toString();
//            String sql = SlowQueryHolder.getCurrentQuery();
//            String explain = logger.explain(sql);
//            logger.logSlowQuery(method, sql, duration, explain);
//        }
//
//        String method = invocation.getMethod().toString();
//        String sql = SlowQueryHolder.getCurrentQuery();
//        String explain = logger.explain(sql);
//        System.out.println("쿼리 탐색");
//        System.out.println(method);
//        System.out.println(sql);
//        System.out.println(explain);
//
//        return result;
//    }
//}
