package com.snailcatch.snailcatch.repository;

import org.springframework.stereotype.Component;

@Component
public class TestRepository {
    public String findSomething() {
        return "real-result";
    }
}
