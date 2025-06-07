package com.snailcatch.snailcatch.global.formatter;

import java.util.*;

public class LogFormatter {

    public static final String[] EXPLAIN_COLUMNS = {
            "id", "select_type", "table", "partitions", "type", "possible_keys",
            "key", "key_len", "ref", "rows", "filtered", "Extra"
    };

    public static String formatLog(String methodName, long duration, String formattedSqls, String explains) {
        return String.format("\n==================== Snail Catch ====================\n" +
                        "Method         : %s\n" +
                        "Execution Time : %d ms\n" +
                        "SQL Queries:\n%s\n\n" +
                        "Execution Plans:\n%s\n" +
                        "=====================================================",
                methodName, duration, formattedSqls.trim(), explains.trim());
    }

    public static String formatExplain(List<Map<String, String>> rowsData) {
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

        // add Header
        sb.append("| ");
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(String.format("%-" + columnWidths.get(col) + "s | ", col));
        }
        sb.append("\n");

        // add Sperator
        sb.append("|");
        for (String col : EXPLAIN_COLUMNS) {
            sb.append("-".repeat(columnWidths.get(col) + 2)).append("|");
        }
        sb.append("\n");

        // add Data
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
