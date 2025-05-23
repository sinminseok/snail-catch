package com.snailcatch.snailcatch.domain.query_execution_log;

import java.time.LocalDateTime;
import java.util.List;

public class QueryExecutionLog implements Comparable<QueryExecutionLog> {
    private final String methodName;
    private final List<String> sqlQueries;
    private final List<String> executionPlans;
    private final LocalDateTime createdAt;
    private final long duration;

    public QueryExecutionLog(String methodName, List<String> sqlQueries, List<String> executionPlans,
                             LocalDateTime createdAt, long duration) {
        this.methodName = methodName;
        this.sqlQueries = sqlQueries;
        this.executionPlans = executionPlans;
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getSqlQueries() {
        return sqlQueries;
    }

    public List<String> getExecutionPlans() {
        return executionPlans;
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
