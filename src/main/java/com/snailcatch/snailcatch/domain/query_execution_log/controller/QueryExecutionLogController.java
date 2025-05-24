package com.snailcatch.snailcatch.domain.query_execution_log.controller;

import com.snailcatch.snailcatch.domain.query_execution_log.QueryExecutionLog;
import com.snailcatch.snailcatch.domain.query_execution_log.repository.InMemoryQueryExecutionLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class QueryExecutionLogController {

    private final InMemoryQueryExecutionLogRepository repository;

    public QueryExecutionLogController(InMemoryQueryExecutionLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<QueryExecutionLog> getAllLogs() {
        return repository.findAllSortedByCreatedAtDesc();
    }
}
