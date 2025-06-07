package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.domain.query_log.QueryLog;

import java.util.List;

public interface LogSender {
    void sendAsync(List<QueryLog> logs);
}