package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.domain.query_log.QueryLog;
import com.snailcatch.snailcatch.domain.query_log.repository.QueryLogRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class BufferedLogDispatchService {

    private final QueryLogRepository repository;
    private final LogSender logSender;

    private final int MAX_BUFFER_SIZE = 10;
    private final Duration MAX_WAIT_TIME = Duration.ofSeconds(10);
    private final Duration CHECK_INTERVAL = Duration.ofSeconds(5);
    private Instant lastFlushTime = Instant.now();

    public BufferedLogDispatchService(QueryLogRepository repository,
                                      LogSender logSender) {
        this.repository = repository;
        this.logSender = logSender;
    }

    @PostConstruct
    public void startDispatcherLoop() {
        Thread loopThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(CHECK_INTERVAL.toMillis());
                    flushIfNeeded();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        loopThread.setDaemon(true);
        loopThread.start();
    }

    public void onNewLog(QueryLog log) {
        repository.save(log);
        if (repository.getSize() >= MAX_BUFFER_SIZE) {
            flush();
        }
    }

    private void flushIfNeeded() {
        if (repository.getSize() == 0) return;
        Duration sinceLastFlush = Duration.between(lastFlushTime, Instant.now());
        if (sinceLastFlush.compareTo(MAX_WAIT_TIME) >= 0) {
            flush();
        }
    }

    private void flush() {
        List<QueryLog> logs = repository.findAll();
        if (logs.isEmpty()) return;
        repository.clear();
        lastFlushTime = Instant.now();
        logSender.sendAsync(logs);
    }
}
