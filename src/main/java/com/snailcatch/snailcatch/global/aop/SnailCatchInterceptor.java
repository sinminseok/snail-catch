package com.snailcatch.snailcatch.global.aop;

import com.snailcatch.snailcatch.domain.query_log.repository.QueryLogRepository;
import com.snailcatch.snailcatch.domain.query_log.collector.QueryCollector;
import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;
import com.snailcatch.snailcatch.global.formatter.LogFormatter;
import com.snailcatch.snailcatch.global.formatter.SqlFormatter;
import com.snailcatch.snailcatch.global.formatter.ExecutionPlanFormatter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A MethodInterceptor implementation that intercepts method executions,
 * collects executed SQL queries, measures execution time,
 * formats and logs the queries along with their execution plans,
 * and saves the logs to a repository.
 */
public class SnailCatchInterceptor implements MethodInterceptor {

    private static final String NEXT_LINE = "\n";
    private static final String DOT = ".";
    private static final String SELECT_QUERY_PREFIX = "select";
    private static final Logger log = LoggerFactory.getLogger(SnailCatchInterceptor.class);

    private final QueryCollector queryCollector;
    private final ExecutionPlanFormatter executionPlanLogger;
    private final DataSource dataSource;
    private final QueryLogRepository queryLogRepository;

    /**
     * Constructor to initialize necessary components for query interception and logging.
     *
     * @param queryCollector collects SQL queries executed during method invocation
     * @param executionPlanLogger generates execution plans for SQL queries
     * @param dataSource database connection source used for explaining queries
     * @param queryLogRepository repository to save query logs
     */
    public SnailCatchInterceptor(QueryCollector queryCollector, ExecutionPlanFormatter executionPlanLogger, DataSource dataSource, QueryLogRepository queryLogRepository) {
        this.queryCollector = queryCollector;
        this.executionPlanLogger = executionPlanLogger;
        this.dataSource = dataSource;
        this.queryLogRepository = queryLogRepository;
    }

    /**
     * Intercepts the target method execution to measure duration,
     * collect SQL queries, log the details, and save query logs.
     *
     * @param invocation the method invocation being intercepted
     * @return the result of the method invocation
     * @throws Throwable if the target method throws any exception
     */
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

    /**
     * Logs detailed information about the intercepted method execution,
     * including formatted SQL queries, execution plans, method signature, and duration.
     *
     * @param invocation the method invocation
     * @param duration execution time in milliseconds
     * @param queries list of collected SQL queries
     */
    private void logQueryDetails(MethodInvocation invocation, long duration, List<String> queries) {
        String formattedSqls = formatSqls(queries);
        String executionPlans = generateExecutionPlans(queries);
        String methodName = getMethodSignature(invocation);
        saveLog(formattedSqls, executionPlans, methodName, duration);
        log.info(LogFormatter.formatLog(methodName, duration, formattedSqls, executionPlans));
    }

    /**
     * Saves the query log to the repository.
     *
     * @param sql formatted SQL queries
     * @param executionPlan generated execution plan string
     * @param methodName method signature where queries were executed
     * @param duration execution time in milliseconds
     */
    private void saveLog(String sql, String executionPlan, String methodName, long duration){
        QueryLog queryExecutionLog = QueryLog.of(methodName, sql, executionPlan, duration);
        queryLogRepository.save(queryExecutionLog);
    }

    /**
     * Formats the list of SQL queries by applying SQL formatting and joining them with new lines.
     *
     * @param queries list of raw SQL query strings
     * @return formatted SQL string
     */
    private String formatSqls(List<String> queries) {
        return queries.stream()
                .map(SqlFormatter::formatSql)
                .collect(Collectors.joining(NEXT_LINE));
    }

    /**
     * Generates execution plans only for SELECT queries by explaining them through the ExecutionPlanFormatter.
     *
     * @param queries list of SQL queries
     * @return concatenated execution plans separated by new lines
     */
    private String generateExecutionPlans(List<String> queries) {
        return queries.stream()
                .filter(this::isSelectQuery)
                .map(query -> executionPlanLogger.explainQuery(dataSource, query))
                .collect(Collectors.joining(NEXT_LINE));
    }

    /**
     * Checks if the query is a SELECT query.
     *
     * @param query the SQL query string
     * @return true if query starts with "select" (case insensitive), false otherwise
     */
    private boolean isSelectQuery(String query) {
        return query.trim().toLowerCase().startsWith(SELECT_QUERY_PREFIX);
    }

    /**
     * Returns the method signature in the format ClassName.methodName.
     *
     * @param invocation the method invocation
     * @return method signature string
     */
    private String getMethodSignature(MethodInvocation invocation) {
        return invocation.getMethod().getDeclaringClass().getSimpleName()
                + DOT + invocation.getMethod().getName();
    }
}
