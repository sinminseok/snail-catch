package com.snailcatch.snailcatch.global.constants;

public class SqlConstants {
    private SqlConstants() {}
    public static final String EXPLAIN = "EXPLAIN ";
    public static final String SELECT_QUERY_PREFIX = "select";
    public static final String[] EXPLAIN_COLUMNS = {
            "id", "select_type", "table", "partitions", "type", "possible_keys",
            "key", "key_len", "ref", "rows", "filtered", "Extra"
    };
}
