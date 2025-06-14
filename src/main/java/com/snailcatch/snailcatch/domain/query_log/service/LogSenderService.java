package com.snailcatch.snailcatch.domain.query_log.service;

import com.snailcatch.snailcatch.config.SnailCatchProperties;
import com.snailcatch.snailcatch.domain.query_log.entity.QueryLog;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * {@link LogSenderService} is a concrete implementation of {@link LogSender} that sends
 * logs to an external HTTP endpoint using {@link RestTemplate}.
 * <p>
 * This implementation supports API key authentication via a configurable header
 * and sends data asynchronously.
 * </p>
 */
@Component
public class LogSenderService implements LogSender {

    /**
     * The endpoint to which query logs are posted
     */
    private static final String SENDER_PATH = "http://15.165.96.198/api/query-logs";

    /**
     * Spring's HTTP client for sending log data
     */
    private final RestTemplate restTemplate;

    /**
     * Application-specific configuration that contains the API key
     */
    private final SnailCatchProperties repositoryProperties;

    /**
     * Constructs a new LogSenderService.
     *
     * @param builder              used to build a {@link RestTemplate} instance
     * @param repositoryProperties configuration that includes the API key
     */
    public LogSenderService(RestTemplateBuilder builder, SnailCatchProperties repositoryProperties) {
        this.restTemplate = builder.build();
        this.repositoryProperties = repositoryProperties;
    }

    /**
     * Sends logs to the configured endpoint asynchronously.
     * <p>
     * If an API key is defined in {@link SnailCatchProperties}, it is added
     * to the request header as "X-API-KEY".
     * </p>
     *
     * @param logs the list of {@link QueryLog} entries to send
     */
    @Override
    @Async
    public void sendAsync(List<QueryLog> logs) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Add API key if available
            String apiKey = repositoryProperties.getApiKey();
            if (apiKey != null && !apiKey.isBlank()) {
                headers.set("X-API-KEY", apiKey);
            }

            HttpEntity<List<QueryLog>> requestEntity = new HttpEntity<>(logs, headers);
            restTemplate.postForObject(SENDER_PATH, requestEntity, Void.class);
        } catch (Exception e) {
            // TODO: Consider replacing with proper logging and retry mechanism
            System.err.println("Failed to send logs: " + e.getMessage());
        }
    }
}
