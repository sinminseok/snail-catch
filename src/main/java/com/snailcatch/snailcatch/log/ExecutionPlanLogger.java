package com.snailcatch.snailcatch.log;

import com.snailcatch.snailcatch.formatter.LogFormatter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExecutionPlanLogger {

    public String explainQuery(DataSource dataSource, String sql) {
        List<Map<String, String>> rowsData = new ArrayList<>();
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("EXPLAIN " + sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (String col : LogFormatter.EXPLAIN_COLUMNS) {
                    row.put(col, nullToDash(rs.getString(col)));
                }
                rowsData.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while explaining query: " + e.getMessage();
        }

        return LogFormatter.formatExplain(rowsData);
    }

    private String nullToDash(String str) {
        return (str == null || str.isEmpty()) ? "-" : str;
    }
}
