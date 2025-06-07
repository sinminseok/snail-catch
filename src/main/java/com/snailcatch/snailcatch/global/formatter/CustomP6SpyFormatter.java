package com.snailcatch.snailcatch.global.formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.snailcatch.snailcatch.global.collector.QueryCollectorHolder;

public class CustomP6SpyFormatter implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql != null && !sql.trim().isEmpty()) {
            QueryCollectorHolder.getCollector().addQuery(sql);
        }
        return sql;
    }
}
