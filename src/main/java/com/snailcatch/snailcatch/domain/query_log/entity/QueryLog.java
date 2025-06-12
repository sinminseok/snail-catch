package com.snailcatch.snailcatch.domain.query_log.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a log of a SQL query executed by the system.
 * <p>
 * Includes the method name, SQL query string, execution plan,
 * execution duration in milliseconds, and the timestamp of execution.
 * </p>
 * <p>
 * This class is immutable and instances are created via the
 * static factory method {@link #of(String, String, String, long)}.
 * </p>
 */
public class QueryLog implements Comparable<QueryLog>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String methodName;
    private final String sqlQuery;
    private final String executionPlan;
    private final LocalDateTime createdAt;
    private final long duration;

    /**
     * Private constructor. Use {@link #of(String, String, String, long)} to create instances.
     *
     * @param methodName     The name of the method where the query was executed.
     * @param sqlQuery       The raw SQL query string.
     * @param executionPlan  The execution plan of the SQL query.
     * @param duration       The time it took to execute the query, in milliseconds.
     */
    private QueryLog(String methodName, String sqlQuery, String executionPlan, long duration) {
        this.methodName = methodName;
        this.sqlQuery = sqlQuery;
        this.executionPlan = executionPlan;
        this.createdAt = LocalDateTime.now();
        this.duration = duration;
    }

    /**
     * Static factory method to create a new {@link QueryLog} instance.
     *
     * @param methodName     The name of the method where the query was executed.
     * @param sqlQuery       The raw SQL query string.
     * @param executionPlan  The execution plan of the SQL query.
     * @param duration       The time it took to execute the query, in milliseconds.
     * @return A new immutable {@link QueryLog} instance.
     */
    public static QueryLog of(String methodName, String sqlQuery, String executionPlan, long duration) {
        return new QueryLog(methodName, sqlQuery, executionPlan, duration);
    }

    /**
     * @return The name of the method that triggered the query.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return The raw SQL query string.
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * @return The execution plan associated with the SQL query.
     */
    public String getExecutionPlan() {
        return executionPlan;
    }

    /**
     * @return The timestamp when the query was executed.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return The execution time of the query in milliseconds.
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Compares this QueryLog with another based on creation time, in descending order.
     * (Most recent log appears first.)
     *
     * @param other The other QueryLog to compare with.
     * @return A negative integer, zero, or a positive integer as this object is more recent than,
     * equal to, or older than the specified object.
     */
    @Override
    public int compareTo(QueryLog other) {
        return other.createdAt.compareTo(this.createdAt);
    }
}
