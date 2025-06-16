package com.snailcatch.snailcatch.global.formatter;

import java.util.*;

import static com.snailcatch.snailcatch.global.constants.FormatConstants.*;
import static com.snailcatch.snailcatch.global.constants.LogMessageTemplates.*;
import static com.snailcatch.snailcatch.global.constants.SqlConstants.*;

/**
 * Utility class for formatting SQL logs and execution plans for better readability.
 */
public class LogFormatter {
    /**
     * Formats a log message with method name, execution time, SQL queries, and execution plans.
     *
     * @param methodName    The name of the method where the query was executed.
     * @param duration      The execution duration in milliseconds.
     * @param formattedSqls The formatted SQL query string(s).
     * @param explains      The formatted execution plan string(s).
     * @return A formatted multiline log string.
     */
    public static String formatLog(String methodName, long duration, String formattedSqls, String explains) {
        return String.format(SNAIL_CATCH_CONSOLE_FORM,
                methodName, duration, formattedSqls.trim(), explains.trim());
    }

    /**
     * Formats the result of an EXPLAIN query into a nicely aligned table string.
     *
     * @param rowsData List of rows, each row represented as a map of column name to value.
     * @return A string representing the formatted execution plan table.
     */
    public static String formatExplain(List<Map<String, String>> rowsData) {
        Map<String, Integer> columnWidths = calculateColumnWidths(rowsData);

        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader(columnWidths));
        sb.append(formatSeparator(columnWidths));
        for (Map<String, String> row : rowsData) {
            sb.append(formatRow(row, columnWidths));
        }

        return sb.toString();
    }

    private static Map<String, Integer> calculateColumnWidths(List<Map<String, String>> rowsData) {
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (String col : EXPLAIN_COLUMNS) {
            columnWidths.put(col, col.length());
        }
        for (Map<String, String> row : rowsData) {
            for (String col : EXPLAIN_COLUMNS) {
                String val = row.getOrDefault(col, HYPHEN);
                columnWidths.put(col, Math.max(columnWidths.get(col), val.length()));
            }
        }
        return columnWidths;
    }

    private static String formatHeader(Map<String, Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(PIPE_WITH_SPACE);
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(String.format(LEFT_JUSTIFY_FORMAT + columnWidths.get(col) + FORMAT_STRING_WITH_PIPE, col));
        }
        sb.append(NEW_LINE);
        return sb.toString();
    }

    private static String formatSeparator(Map<String, Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(PIPE);
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(HYPHEN.repeat(columnWidths.get(col) + 2)).append(PIPE);
        }
        sb.append(NEW_LINE);
        return sb.toString();
    }

    private static String formatRow(Map<String, String> row, Map<String, Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(PIPE_WITH_SPACE);
        for (String col : EXPLAIN_COLUMNS) {
            String value = row.getOrDefault(col, HYPHEN);
            sb.append(String.format(LEFT_JUSTIFY_FORMAT + columnWidths.get(col) + FORMAT_STRING_WITH_PIPE, value));
        }
        sb.append(NEW_LINE);
        return sb.toString();
    }
}
