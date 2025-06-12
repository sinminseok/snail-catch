package com.snailcatch.snailcatch.global.formatter;

import java.util.*;

/**
 * Utility class for formatting SQL logs and execution plans for better readability.
 */
public class LogFormatter {

    /**
     * Columns used in the EXPLAIN query result for SQL execution plans.
     */
    public static final String[] EXPLAIN_COLUMNS = {
            "id", "select_type", "table", "partitions", "type", "possible_keys",
            "key", "key_len", "ref", "rows", "filtered", "Extra"
    };

    /**
     * Formats a log message with method name, execution time, SQL queries, and execution plans.
     *
     * @param methodName   The name of the method where the query was executed.
     * @param duration     The execution duration in milliseconds.
     * @param formattedSqls The formatted SQL query string(s).
     * @param explains     The formatted execution plan string(s).
     * @return A formatted multiline log string.
     */
    public static String formatLog(String methodName, long duration, String formattedSqls, String explains) {
        return String.format("\n==================== Snail Catch ====================\n" +
                        "Method         : %s\n" +
                        "Execution Time : %d ms\n" +
                        "SQL Queries:\n%s\n\n" +
                        "Execution Plans:\n%s\n" +
                        "=====================================================",
                methodName, duration, formattedSqls.trim(), explains.trim());
    }

    /**
     * Formats the result of an EXPLAIN query into a nicely aligned table string.
     *
     * @param rowsData List of rows, each row represented as a map of column name to value.
     * @return A string representing the formatted execution plan table.
     */
    public static String formatExplain(List<Map<String, String>> rowsData) {
        // Calculate the maximum width for each column to align table properly
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (String col : EXPLAIN_COLUMNS) {
            columnWidths.put(col, col.length());
        }
        for (Map<String, String> row : rowsData) {
            for (String col : EXPLAIN_COLUMNS) {
                String val = row.getOrDefault(col, "-");
                columnWidths.put(col, Math.max(columnWidths.get(col), val.length()));
            }
        }

        StringBuilder sb = new StringBuilder();

        // Add header row
        sb.append("| ");
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(String.format("%-" + columnWidths.get(col) + "s | ", col));
        }
        sb.append("\n");

        // Add separator row
        sb.append("|");
        for (String col : EXPLAIN_COLUMNS) {
            sb.append("-".repeat(columnWidths.get(col) + 2)).append("|");
        }
        sb.append("\n");

        // Add data rows
        for (Map<String, String> row : rowsData) {
            sb.append("| ");
            for (String col : EXPLAIN_COLUMNS) {
                sb.append(String.format("%-" + columnWidths.get(col) + "s | ", row.getOrDefault(col, "-")));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
