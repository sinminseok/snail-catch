package com.snailcatch.snailcatch.domain.query_execution_log;

import java.time.LocalDateTime;

public class QueryExecutionLog implements Comparable<QueryExecutionLog> {
    private final String methodName;
    private final String sqlQuery;
    private final String executionPlan;
    private final LocalDateTime createdAt;
    private final long duration;

    private QueryExecutionLog(String methodName, String sqlQuery, String executionPlan, long duration) {
        this.methodName = methodName;
        this.sqlQuery = sqlQuery;
        this.executionPlan = executionPlan;
        this.createdAt = LocalDateTime.now();
        this.duration = duration;
    }

    public static QueryExecutionLog of(String methodName, String sqlQuery, String executionPlan, long duration) {
        return new QueryExecutionLog(methodName, sqlQuery, executionPlan, duration);
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public String getExecutionPlan() {
        return executionPlan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getDuration() {
        return duration;
    }

    // createdAt 기준 내림차순 (최근 로그가 먼저 오도록)
    @Override
    public int compareTo(QueryExecutionLog other) {
        return other.createdAt.compareTo(this.createdAt);
    }

    @Override
    public String toString() {
        return String.format("Method: %s | CreatedAt: %s | Duration: %dms", methodName, createdAt, duration);
    }
}
