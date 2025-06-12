package com.snailcatch.snailcatch.domain.query_log.collector.impl;

import com.snailcatch.snailcatch.domain.query_log.collector.QueryCollector;
import com.snailcatch.snailcatch.global.formatter.CustomP6SpyFormatter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of QueryCollector that stores SQL queries in a ThreadLocal variable.
 *
 * <p>This allows each thread to maintain its own independent list of queries,
 * ensuring thread safety and preventing cross-thread data contamination.</p>
 */
public class ThreadLocalQueryCollector implements QueryCollector {

    /**
     * ThreadLocal container holding a list of SQL queries per thread.
     * Initialized with an empty ArrayList for each thread.
     */
    private static final ThreadLocal<List<String>> queryCollector = ThreadLocal.withInitial(ArrayList::new);

    /**
     * Adds a SQL query string to the current thread's query list.
     *
     * @param query the SQL query string to add
     */
    @Override
    public void addQuery(String query) {
        System.out.println("add query==" + query);
        queryCollector.get().add(query);
    }

    /**
     * Retrieves the list of SQL queries collected for the current thread.
     *
     * @return the list of SQL query strings
     */
    @Override
    public List<String> getQueries() {
        return queryCollector.get();
    }

    /**
     * Clears the stored SQL queries for the current thread to prevent memory leaks.
     */
    @Override
    public void clear() {
        queryCollector.remove();
    }
}
