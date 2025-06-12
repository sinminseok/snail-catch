package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;
import com.snailcatch.snailcatch.domain.query_log.repository.QueryLogRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * BufferedLogDispatchService is responsible for collecting and dispatching {@link QueryLog} entries
 * in batches. It uses a buffer and dispatches logs either when:
 * <ul>
 *   <li>The buffer size reaches {@code MAX_BUFFER_SIZE}</li>
 *   <li>The elapsed time since the last flush exceeds {@code MAX_WAIT_TIME}</li>
 * </ul>
 * <p>
 * This service runs an internal background thread to periodically check and flush the logs.
 * </p>
 */
@Component
public class BufferedLogDispatchService {

    /** Repository used for buffering QueryLogs in memory */
    private final QueryLogRepository repository;

    /** Sender component responsible for dispatching logs to an external destination */
    private final LogSender logSender;

    /** Maximum number of logs to buffer before triggering a flush */
    private final int MAX_BUFFER_SIZE = 100;

    /** Maximum time to wait before flushing the buffer, even if it's not full */
    private final Duration MAX_WAIT_TIME = Duration.ofSeconds(10);

    /** Interval at which to check whether the buffer should be flushed */
    private final Duration CHECK_INTERVAL = Duration.ofSeconds(5);

    /** Timestamp of the last buffer flush */
    private Instant lastFlushTime = Instant.now();

    /**
     * Constructs the BufferedLogDispatchService with required dependencies.
     *
     * @param repository the in-memory log buffer
     * @param logSender  the component responsible for asynchronous log dispatching
     */
    public BufferedLogDispatchService(QueryLogRepository repository, LogSender logSender) {
        this.repository = repository;
        this.logSender = logSender;
    }

    /**
     * Initializes the dispatcher loop.
     * <p>
     * This method starts a background daemon thread that periodically checks
     * if the log buffer should be flushed based on time.
     * </p>
     */
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
        loopThread.setDaemon(true); // does not block application shutdown
        loopThread.start();
    }

    /**
     * Handles a newly created {@link QueryLog} entry.
     * <p>
     * The log is stored in the buffer. If the buffer size exceeds the max limit,
     * a flush is triggered immediately.
     * </p>
     *
     * @param log the log entry to store
     */
    public void onNewLog(QueryLog log) {
        repository.save(log);
        if (repository.getSize() >= MAX_BUFFER_SIZE) {
            flush();
        }
    }

    /**
     * Flushes the buffer if the time since the last flush exceeds {@code MAX_WAIT_TIME}.
     * <p>
     * Called periodically by the dispatcher thread.
     * </p>
     */
    private void flushIfNeeded() {
        if (repository.getSize() == 0) return;
        Duration sinceLastFlush = Duration.between(lastFlushTime, Instant.now());
        if (sinceLastFlush.compareTo(MAX_WAIT_TIME) >= 0) {
            flush();
        }
    }

    /**
     * Flushes the log buffer and dispatches the logs using {@link LogSender}.
     * <p>
     * After sending, the buffer is cleared and the timestamp is updated.
     * </p>
     */
    private void flush() {
        List<QueryLog> logs = repository.findAll();
        if (logs.isEmpty()) return;
        repository.clear();
        lastFlushTime = Instant.now();
        logSender.sendAsync(logs);
    }
}
