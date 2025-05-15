package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slowquery")
public class SlowQueryProperties {

    private String repositoryPointcut;

    public String getRepositoryPointcut() {
        return repositoryPointcut;
    }

    public void setRepositoryPointcut(String repositoryPointcut) {
        this.repositoryPointcut = repositoryPointcut;
    }
}
