package com.snailcatch.snailcatch.global.formatter;

/**
 * Utility class for formatting SQL queries using the MySQL dialect.
 */
public class SqlFormatter {

    /**
     * Formats a single SQL query string using the MySQL formatter.
     *
     * @param sql The raw SQL query string.
     * @return The formatted SQL query string.
     */
    public static String formatSql(String sql) {
        return com.github.vertical_blank.sqlformatter.SqlFormatter.of("mysql").format(sql);
    }
}
