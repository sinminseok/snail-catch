package com.snailcatch.snailcatch.collector;

public class SlowQueryCollectorHolder {
    private static SlowQueryCollector collector;

    public static void setCollector(SlowQueryCollector slowQueryCollector) {
        collector = slowQueryCollector;
    }

    public static SlowQueryCollector getCollector() {
        return collector;
    }
}
