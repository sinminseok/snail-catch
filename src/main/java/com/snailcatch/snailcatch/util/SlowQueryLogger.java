package com.snailcatch.snailcatch.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.stream.Collectors;

@Component
public class SlowQueryLogger {

    private final JdbcTemplate jdbcTemplate;
    //private final LogSender logSender; // 비동기 전송용 클래스

    public SlowQueryLogger(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        //this.logSender = logSender;
    }

    public String explain(String sql) {
        try {
            return jdbcTemplate.queryForList("EXPLAIN " + sql)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "EXPLAIN failed: " + e.getMessage();
        }
    }

    public void logSlowQuery(String methodName, String sql, long duration, String explainPlan) {
        SlowQueryLog log = new SlowQueryLog(methodName, sql, duration, explainPlan);
        //logSender.send(log); // 큐에 저장 후 비동기 전송
    }
}

