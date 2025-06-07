package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "snail-catch")
public class SnailCatchProperties {

    private String repositoryPointcut;
    private String apiKey;

    public String getRepositoryPointcut() {
        return repositoryPointcut;
    }

    public void setRepositoryPointcut(String repositoryPointcut) {
        this.repositoryPointcut = repositoryPointcut;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}