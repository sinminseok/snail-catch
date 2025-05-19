package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slowquery")
public class SlowQueryProperties {
    private String repositoryPointcut;
    private long thresholdMillis;

    public String getRepositoryPointcut() {
        return repositoryPointcut;
    }

    public void setRepositoryPointcut(String repositoryPointcut) {
        this.repositoryPointcut = repositoryPointcut;
    }

    public long getThresholdMillis() {
        return thresholdMillis;
    }

    public void setThresholdMillis(long thresholdMillis) {
        this.thresholdMillis = thresholdMillis;
    }
}