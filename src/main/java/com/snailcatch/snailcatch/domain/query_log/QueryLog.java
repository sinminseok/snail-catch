package com.snailcatch.snailcatch.domain.query_log;

import java.time.LocalDateTime;

public class QueryLog implements Comparable<QueryLog> {
    private final String methodName;
    private final String sqlQuery;
    private final String executionPlan;
    private final LocalDateTime createdAt;
    private final long duration;

    private QueryLog(String methodName, String sqlQuery, String executionPlan, long duration) {
        this.methodName = methodName;
        this.sqlQuery = sqlQuery;
        this.executionPlan = executionPlan;
        this.createdAt = LocalDateTime.now();
        this.duration = duration;
    }

    public static QueryLog of(String methodName, String sqlQuery, String executionPlan, long duration) {
        return new QueryLog(methodName, sqlQuery, executionPlan, duration);
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
    public int compareTo(QueryLog other) {
        return other.createdAt.compareTo(this.createdAt);
    }
}
