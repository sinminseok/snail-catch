package com.snailcatch.snailcatch.collector;

public class QueryCollectorHolder {
    private static QueryCollector collector;

    public static void setCollector(QueryCollector slowQueryCollector) {
        collector = slowQueryCollector;
    }

    public static QueryCollector getCollector() {
        return collector;
    }
}
