package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;

import java.util.List;

/**
 * LogSender is an abstraction for sending collected {@link QueryLog} entries
 * to an external service or storage asynchronously.
 */
public interface LogSender {

    /**
     * Sends the given list of logs asynchronously to the configured destination.
     *
     * @param logs the list of {@link QueryLog} entries to send
     */
    void sendAsync(List<QueryLog> logs);
}
