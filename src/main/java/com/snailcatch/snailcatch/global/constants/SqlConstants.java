package com.snailcatch.snailcatch.global.constants;

/**
 * Constants used for constructing and interpreting SQL queries,
 * especially for generating and parsing EXPLAIN query results in MySQL.
 */
public final class SqlConstants {

    private SqlConstants() {
        // Prevent instantiation
    }

    /**
     * The prefix used to generate EXPLAIN queries.
     * Must be followed by a space before the SELECT query.
     * Example: "EXPLAIN SELECT * FROM users"
     */
    public static final String EXPLAIN = "EXPLAIN ";

    /**
     * Prefix string used to identify SELECT queries.
     * Used in logic that conditionally applies EXPLAIN only to SELECT queries.
     */
    public static final String SELECT_QUERY_PREFIX = "select";

    /**
     * Columns returned by the MySQL EXPLAIN statement.
     * These are used to format or parse the result of an EXPLAIN query.
     */
    public static final String[] EXPLAIN_COLUMNS = {
            "id", "select_type", "table", "partitions", "type", "possible_keys",
            "key", "key_len", "ref", "rows", "filtered", "Extra"
    };
}
