package com.snailcatch.snailcatch.util;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AsyncBulkLogSender implements LogSender {

    private final BlockingQueue<SlowQueryLog> buffer = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService senderPool = Executors.newFixedThreadPool(1);

    private final int batchSize = 10;
    private final int flushIntervalMillis = 5000;

    private final RestTemplate restTemplate = new RestTemplate(); // or WebClient

    private final String logServerUrl = "http://logserver.com/api/logs"; // 외부 수신 서버 주소

    public AsyncBulkLogSender() {
        // 주기적 flush
    }

    @Override
    public void send(SlowQueryLog log) {
        buffer.offer(log); // 큐에 넣기
        if (buffer.size() >= batchSize) {
            flushInternal(); // 조건 만족시 즉시 flush
        }
    }

    @Override
    public void flush() {
        flushInternal();
    }

    private void flushInternal() {
        if (buffer.isEmpty()) return;

        List<SlowQueryLog> batch = new ArrayList<>();
        buffer.drainTo(batch, batchSize); // 최대 batchSize만큼 가져오기

        if (!batch.isEmpty()) {
            senderPool.submit(() -> {
                try {
                    HttpEntity<List<SlowQueryLog>> entity = new HttpEntity<>(batch);
                    restTemplate.postForEntity(logServerUrl, entity, Void.class);
                } catch (Exception e) {
                    // 실패 처리: 재시도 로직, 로그, dead-letter 등
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void shutdown() {
        flushInternal(); // 마지막 flush
        scheduler.shutdown();
        senderPool.shutdown();
    }
}