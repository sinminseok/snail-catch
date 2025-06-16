package com.snailcatch.snailcatch.global.constants;

public final class LogMessageTemplates {
    private LogMessageTemplates() {}
    public static final String SNAIL_CATCH_CONSOLE_FORM = "\n==================== Snail Catch ====================\n" +
            "Method         : %s\n" +
            "Execution Time : %d ms\n" +
            "SQL Queries:\n%s\n\n" +
            "Execution Plans:\n%s\n" +
            "========================================================";
}