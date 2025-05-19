package com.snailcatch.snailcatch.util;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class SlowQueryInspector implements StatementInspector {
    @Override
    public String inspect(String sql) {
        SlowQueryHolder.setCurrentQuery(sql);
        return sql;
    }
}