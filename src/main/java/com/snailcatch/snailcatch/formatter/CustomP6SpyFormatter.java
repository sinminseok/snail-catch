package com.snailcatch.snailcatch.formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.snailcatch.snailcatch.collector.SlowQueryCollectorHolder;

public class CustomP6SpyFormatter implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql != null && !sql.trim().isEmpty()) {
            SlowQueryCollectorHolder.getCollector().addQuery(sql);
        }
        return sql;
    }
}
