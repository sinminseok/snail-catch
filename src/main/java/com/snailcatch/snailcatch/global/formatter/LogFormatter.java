package com.snailcatch.snailcatch.global.formatter;

import java.util.*;

import static com.snailcatch.snailcatch.global.constants.FormatConstants.*;
import static com.snailcatch.snailcatch.global.constants.LogMessageTemplates.*;
import static com.snailcatch.snailcatch.global.constants.SqlConstants.*;

/**
 * Utility class for formatting SQL execution logs and EXPLAIN query results.
 *
 * <p>This class provides methods to format detailed log messages and neatly
 * formatted execution plans, aligning columns and producing console-friendly output.</p>
 */
public class LogFormatter {

    /**
     * Formats a comprehensive log message including method name, execution time,
     * SQL queries, and their execution plans.
     *
     * @param methodName   the name of the method executing the SQL queries
     * @param duration     execution duration in milliseconds
     * @param formattedSqls formatted SQL query string(s)
     * @param explains     formatted EXPLAIN plan string(s)
     * @return formatted log message ready for console output
     */
    public static String formatLog(String methodName, long duration, String formattedSqls, String explains) {
        return String.format(SNAIL_CATCH_CONSOLE_FORM,
                methodName, duration, formattedSqls.trim(), explains.trim());
    }

    /**
     * Formats the EXPLAIN query result rows into a well-aligned table-like string.
     *
     * @param rowsData list of maps, each representing a row of EXPLAIN output with column name as key
     * @return formatted multi-line string representing the EXPLAIN output
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

    /**
     * Calculates the maximum width needed for each EXPLAIN column to properly align the output.
     *
     * @param rowsData the EXPLAIN query result rows
     * @return map of column names to their respective max widths
     */
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

    /**
     * Formats the header row of the EXPLAIN output table.
     *
     * @param columnWidths map of column widths for alignment
     * @return formatted header string
     */
    private static String formatHeader(Map<String, Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(PIPE_WITH_SPACE);
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(String.format(LEFT_JUSTIFY_FORMAT + columnWidths.get(col) + FORMAT_STRING_WITH_PIPE, col));
        }
        sb.append(NEW_LINE);
        return sb.toString();
    }

    /**
     * Formats the separator row composed of dashes to visually separate the header and data rows.
     *
     * @param columnWidths map of column widths for alignment
     * @return formatted separator string
     */
    private static String formatSeparator(Map<String, Integer> columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append(PIPE);
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(HYPHEN.repeat(columnWidths.get(col) + 2)).append(PIPE);
        }
        sb.append(NEW_LINE);
        return sb.toString();
    }

    /**
     * Formats a single data row from the EXPLAIN query results.
     *
     * @param row          map representing a single EXPLAIN row with column names as keys
     * @param columnWidths map of column widths for alignment
     * @return formatted row string
     */
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
