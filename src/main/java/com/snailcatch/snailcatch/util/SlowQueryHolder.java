package com.snailcatch.snailcatch.util;

public class SlowQueryHolder {
    private static final ThreadLocal<String> currentQuery = new ThreadLocal<>();

    public static void setCurrentQuery(String query) {
        currentQuery.set(query);
    }

    public static String getCurrentQuery() {
        return currentQuery.get();
    }

    public static void clear() {
        currentQuery.remove();
    }
}

