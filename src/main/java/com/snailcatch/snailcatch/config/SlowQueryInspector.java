package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.util.SlowQueryHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public class SlowQueryInspector implements StatementInspector {
    @Override
    public String inspect(String sql) {
        SlowQueryHolder.setCurrentQuery(sql);
        return sql;
    }
}
