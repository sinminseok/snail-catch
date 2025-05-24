package com.snailcatch.snailcatch.domain.query_execution_log.repository;

import com.snailcatch.snailcatch.domain.query_execution_log.QueryExecutionLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Repository
public class InMemoryQueryExecutionLogRepository {

    private final PriorityQueue<QueryExecutionLog> queue;
    private final int maxSize;

    public InMemoryQueryExecutionLogRepository(int maxSize) {
        this.queue = new PriorityQueue<>();
        this.maxSize = maxSize;
    }

    public synchronized void save(QueryExecutionLog log) {
        if (queue.size() >= maxSize) {
            queue.poll();
        }
        queue.offer(log);
    }

    public synchronized List<QueryExecutionLog> findAllSortedByCreatedAtDesc() {
        return queue.stream()
                .sorted() //createdAt 내림차순
                .collect(Collectors.toList());
    }

    public synchronized void clear() {
        queue.clear();
    }

    public synchronized int size() {
        return queue.size();
    }
}
