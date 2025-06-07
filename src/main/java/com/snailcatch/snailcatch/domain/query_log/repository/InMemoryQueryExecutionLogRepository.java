package com.snailcatch.snailcatch.domain.query_log.repository;

import com.snailcatch.snailcatch.domain.query_log.QueryLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Repository
public class InMemoryQueryExecutionLogRepository implements QueryLogRepository {

    private final PriorityQueue<QueryLog> queue;
    private final int maxSize;

    public InMemoryQueryExecutionLogRepository() {
        this.queue = new PriorityQueue<>();
        this.maxSize = 10000;
    }

    @Override
    public synchronized void save(QueryLog log) {
        if (queue.size() >= maxSize) {
            queue.poll();
        }
        queue.offer(log);
    }

    @Override
    public synchronized List<QueryLog> findAll() {
        return queue.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void clear() {
        queue.clear();
    }

    @Override
    public synchronized int getSize() {
        return queue.size();
    }
}
