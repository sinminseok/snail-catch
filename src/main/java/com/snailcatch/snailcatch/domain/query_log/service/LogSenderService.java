package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.config.SnailCatchProperties;
import com.snailcatch.snailcatch.domain.query_log.QueryLog;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LogSenderService implements LogSender{

    private static final String SENDER_PATH = "http://localhost:8081/api/query-logs";
    private final RestTemplate restTemplate;
    private final SnailCatchProperties repositoryProperties;

    public LogSenderService(RestTemplateBuilder builder, SnailCatchProperties repositoryProperties) {
        this.restTemplate = builder.build();
        this.repositoryProperties = repositoryProperties;
    }

    @Override
    @Async
    public void sendAsync(List<QueryLog> logs) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String apiKey = repositoryProperties.getApiKey();
            if (apiKey != null && !apiKey.isBlank()) {
                headers.set("X-API-KEY", apiKey);
            }
            HttpEntity<List<QueryLog>> requestEntity = new HttpEntity<>(logs, headers);
            restTemplate.postForObject(SENDER_PATH, requestEntity, Void.class);
        } catch (Exception e) {
            // 실패 시 로깅 + 추후 재시도 로직 필요
            System.err.println("Failed to send logs: " + e.getMessage());
        }
    }
}

