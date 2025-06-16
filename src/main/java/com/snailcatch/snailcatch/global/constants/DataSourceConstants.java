package com.snailcatch.snailcatch.global.constants;

/**
 * Constants related to data source configuration, including JDBC driver class names and URL prefixes.
 */
public class DataSourceConstants {

    private DataSourceConstants() {
        // Prevent instantiation
    }

    /**
     * Fully qualified class name of the P6Spy JDBC driver.
     * This driver enables SQL query logging and monitoring by intercepting JDBC calls.
     */
    public static final String P6SPY_DRIVER_CLASS_NAME = "com.p6spy.engine.spy.P6SpyDriver";

    /**
     * Standard JDBC URL prefix for connecting to a MySQL database.
     */
    public static final String MYSQL_JDBC_PREFIX = "jdbc:mysql";

    /**
     * JDBC URL prefix used when integrating P6Spy with MySQL for query logging.
     * This allows transparent SQL profiling without changing business logic.
     */
    public static final String P6SPY_JDBC_PREFIX = "jdbc:p6spy:mysql";
}
