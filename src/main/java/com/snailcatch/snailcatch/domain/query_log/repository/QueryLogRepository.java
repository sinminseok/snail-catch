package com.snailcatch.snailcatch.domain.query_log.repository;

import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;

import java.util.List;

/**
 * Repository interface for managing {@link QueryLog} entries.
 * <p>
 * Provides methods to save, retrieve, clear, and inspect query logs.
 * Designed to be implemented either with in-memory or persistent storage.
 * </p>
 */
public interface QueryLogRepository {

    /**
     * Saves a single {@link QueryLog} entry.
     *
     * @param queryLog the log entry to be stored
     */
    void save(QueryLog queryLog);

    /**
     * Retrieves all stored {@link QueryLog} entries.
     *
     * @return a list of all saved query logs
     */
    List<QueryLog> findAll();

    /**
     * Clears all stored {@link QueryLog} entries from the repository.
     */
    void clear();

    /**
     * Returns the current number of stored {@link QueryLog} entries.
     *
     * @return number of stored logs
     */
    int getSize();
}
