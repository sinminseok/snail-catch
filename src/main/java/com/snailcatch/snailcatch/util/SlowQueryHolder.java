package com.snailcatch.snailcatch.util;

public class SlowQueryHolder {
    private static final ThreadLocal<String> queryHolder = new ThreadLocal<>();

    public static void setCurrentQuery(String query) {
        queryHolder.set(query);
    }

    public static String getCurrentQuery() {
        return queryHolder.get();
    }

    public static void clear() {
        queryHolder.remove();
    }
}
