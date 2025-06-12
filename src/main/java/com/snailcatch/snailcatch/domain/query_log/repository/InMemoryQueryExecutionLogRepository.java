package com.snailcatch.snailcatch.domain.query_log.repository;

import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * In-memory implementation of {@link QueryLogRepository}.
 * <p>
 * Stores query logs in a priority queue, sorted by most recent first (based on {@link QueryLog#compareTo}).
 * Designed for development, debugging, or SDK usage where persistence is not required.
 * </p>
 * <p>
 * This implementation is thread-safe using synchronized methods, and limits storage to {@code maxSize} entries.
 * When the maximum size is reached, the oldest entry is evicted.
 * </p>
 */
@Repository
public class InMemoryQueryExecutionLogRepository implements QueryLogRepository {

    private final PriorityQueue<QueryLog> queue;
    private final int maxSize;

    /**
     * Constructs an in-memory log repository with a default maximum size of 10,000 entries.
     */
    public InMemoryQueryExecutionLogRepository() {
        this.queue = new PriorityQueue<>();
        this.maxSize = 10_000;
    }

    /**
     * Saves a new {@link QueryLog} into the repository.
     * <p>If the size limit is reached, the oldest log entry is removed.</p>
     *
     * @param log the query log to save
     */
    @Override
    public synchronized void save(QueryLog log) {
        if (queue.size() >= maxSize) {
            queue.poll(); // remove oldest
        }
        queue.offer(log); // insert new
    }

    /**
     * Retrieves all stored query logs sorted from most recent to oldest.
     *
     * @return list of all stored query logs
     */
    @Override
    public synchronized List<QueryLog> findAll() {
        return queue.stream()
                .sorted() // uses compareTo (latest first)
                .collect(Collectors.toList());
    }

    /**
     * Removes all stored logs from memory.
     */
    @Override
    public synchronized void clear() {
        queue.clear();
    }

    /**
     * Returns the current number of stored query logs.
     *
     * @return the size of the log queue
     */
    @Override
    public synchronized int getSize() {
        return queue.size();
    }
}
