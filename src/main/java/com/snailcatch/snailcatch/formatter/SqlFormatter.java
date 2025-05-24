package com.snailcatch.snailcatch.formatter;

public class SqlFormatter {

    public static String formatSql(String sql) {
        return com.github.vertical_blank.sqlformatter.SqlFormatter.of("mysql").format(sql);
    }

}
