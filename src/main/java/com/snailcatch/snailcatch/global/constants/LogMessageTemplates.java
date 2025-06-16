package com.snailcatch.snailcatch.global.constants;

/**
 * Predefined log message templates used for consistent and formatted console logging.
 *
 * <p>This class currently provides a format string for logging SQL query execution details,
 * including method name, execution time, SQL queries, and execution plans.</p>
 */
public final class LogMessageTemplates {

    private LogMessageTemplates() {
        // Prevent instantiation
    }

    /**
     * Template for logging detailed information about SQL execution.
     *
     * <p>Format parameters:</p>
     * <ol>
     *   <li>Method name (String)</li>
     *   <li>Execution time in milliseconds (long)</li>
     *   <li>Formatted SQL queries (String)</li>
     *   <li>Formatted execution plans (String)</li>
     * </ol>
     */
    public static final String SNAIL_CATCH_CONSOLE_FORM = "\n==================== Snail Catch ====================\n" +
            "Method         : %s\n" +
            "Execution Time : %d ms\n" +
            "SQL Queries:\n%s\n\n" +
            "Execution Plans:\n%s\n" +
            "========================================================";
}
