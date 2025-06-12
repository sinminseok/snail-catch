package com.snailcatch.snailcatch.global.formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.snailcatch.snailcatch.domain.query_log.collector.QueryCollectorHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom formatter for P6Spy that intercepts SQL queries executed by the application.
 *
 * It captures non-empty SQL statements and adds them to the QueryCollector for later processing or logging.
 */
public class CustomP6SpyFormatter implements MessageFormattingStrategy {
    /**
     * Formats the SQL message intercepted by P6Spy.
     * If the SQL is not null or empty, it adds the query to the global QueryCollector.
     *
     * @param connectionId the ID of the database connection
     * @param now the current timestamp
     * @param elapsed the elapsed time in milliseconds for the query execution
     * @param category the category of the message
     * @param prepared the prepared statement before parameter substitution
     * @param sql the actual SQL query string
     * @param url the JDBC URL of the database connection
     * @return the original SQL query string unmodified
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        System.out.println("Collector instance: " + QueryCollectorHolder.getCollector());

        System.out.println("sql ====" + sql);
        if (sql != null && !sql.trim().isEmpty()) {
            QueryCollectorHolder.getCollector().addQuery(sql);
        }
        return sql;
    }
}
