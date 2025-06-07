package com.snailcatch.snailcatch.domain.query_log.repository;

import com.snailcatch.snailcatch.domain.query_log.QueryLog;

import java.util.List;

public interface QueryLogRepository {

    void save(QueryLog queryLog);

    List<QueryLog> findAll();

    void clear();

    int getSize();
}
