package com.snailcatch.snailcatch.domain.query_log.collector;

import java.util.List;

/**
 * Interface defining methods for collecting SQL queries during execution.
 */
public interface QueryCollector {

    /**
     * Adds a SQL query string to the collector.
     *
     * @param query the SQL query string to be added
     */
    void addQuery(String query);

    /**
     * Retrieves all collected SQL queries.
     *
     * @return a list of collected SQL query strings
     */
    List<String> getQueries();

    /**
     * Clears all collected SQL queries from the collector.
     */
    void clear();
}
