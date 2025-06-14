package com.snailcatch.snailcatch.global.formatter;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A formatter class that generates and formats SQL execution plans using the EXPLAIN statement.
 * <p>
 * It connects to the database via the provided DataSource,
 * executes EXPLAIN on a given SQL query, and returns the result in structured formats.
 */
@Component
public class ExecutionPlanFormatter {

    /**
     * Executes the EXPLAIN statement for the given SQL query and returns a formatted string representation
     * of the execution plan.
     *
     * @param dataSource the DataSource to obtain a DB connection
     * @param sql        the SQL query to explain
     * @return formatted string of the execution plan or error message if an exception occurs
     */
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

    /**
     * Helper method to convert null or empty strings to a dash ("-").
     *
     * @param str input string
     * @return original string if not null/empty, otherwise "-"
     */
    private String nullToDash(String str) {
        return (str == null || str.isEmpty()) ? "-" : str;
    }
}
