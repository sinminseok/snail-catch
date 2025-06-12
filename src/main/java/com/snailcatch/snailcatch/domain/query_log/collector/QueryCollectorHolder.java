package com.snailcatch.snailcatch.domain.query_log.collector;

/**
 * Holder class for a global QueryCollector instance.
 * Provides static methods to set and get the current QueryCollector.
 */
public class QueryCollectorHolder {

    // The global QueryCollector instance
    private static QueryCollector collector;

    /**
     * Sets the global QueryCollector instance.
     *
     * @param slowQueryCollector the QueryCollector instance to set
     */
    public static void setCollector(QueryCollector slowQueryCollector) {
        collector = slowQueryCollector;
    }

    /**
     * Returns the global QueryCollector instance.
     *
     * @return the currently set QueryCollector instance
     */
    public static QueryCollector getCollector() {
        return collector;
    }
}
