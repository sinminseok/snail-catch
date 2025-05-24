package com.snailcatch.snailcatch.aop.interceptor;

import com.snailcatch.snailcatch.collector.QueryCollector;
import com.snailcatch.snailcatch.domain.query_execution_log.QueryExecutionLog;
import com.snailcatch.snailcatch.domain.query_execution_log.repository.InMemoryQueryExecutionLogRepository;
import com.snailcatch.snailcatch.formatter.LogFormatter;
import com.snailcatch.snailcatch.formatter.SqlFormatter;
import com.snailcatch.snailcatch.formatter.ExecutionPlanFormatter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SlowQueryInterceptor implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SlowQueryInterceptor.class);

    private final QueryCollector queryCollector;
    private final ExecutionPlanFormatter executionPlanLogger;
    private final DataSource dataSource;
    private final InMemoryQueryExecutionLogRepository inMemoryQueryExecutionLogRepository;

    public SlowQueryInterceptor(QueryCollector queryCollector,
                                ExecutionPlanFormatter executionPlanLogger,
                                DataSource dataSource,
                                InMemoryQueryExecutionLogRepository inMemoryQueryExecutionLogRepository) {
        this.queryCollector = queryCollector;
        this.executionPlanLogger = executionPlanLogger;
        this.dataSource = dataSource;
        this.inMemoryQueryExecutionLogRepository = inMemoryQueryExecutionLogRepository;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        queryCollector.clear();

        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        long duration = System.currentTimeMillis() - startTime;

        List<String> collectedQueries = new ArrayList<>(queryCollector.getQueries());
        logQueryDetails(invocation, duration, collectedQueries);
        queryCollector.clear();
        return result;
    }

    private void logQueryDetails(MethodInvocation invocation, long duration, List<String> queries) {
        String formattedSqls = formatSqls(queries);
        String executionPlans = generateExecutionPlans(queries);
        String methodName = getMethodSignature(invocation);
        saveLog(formattedSqls, executionPlans, methodName, duration);
        log.info(LogFormatter.formatLog(methodName, duration, formattedSqls, executionPlans));
    }

    private void saveLog(String sql, String executionPlan, String methodName, long duration){
        QueryExecutionLog queryExecutionLog = QueryExecutionLog.of(methodName, sql, executionPlan, duration);
        inMemoryQueryExecutionLogRepository.save(queryExecutionLog);
    }



    private String formatSqls(List<String> queries) {
        return queries.stream()
                .map(SqlFormatter::formatSql)
                .collect(Collectors.joining("\n"));
    }

    private String generateExecutionPlans(List<String> queries) {
        return queries.stream()
                .filter(this::isSelectQuery)
                .map(query -> executionPlanLogger.explainQuery(dataSource, query))
                .collect(Collectors.joining("\n"));
    }

    private boolean isSelectQuery(String query) {
        return query.trim().toLowerCase().startsWith("select");
    }

    private String getMethodSignature(MethodInvocation invocation) {
        return invocation.getMethod().getDeclaringClass().getSimpleName()
                + "." + invocation.getMethod().getName();
    }
}
