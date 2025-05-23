package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slowquery")
public class RepositoryProperties {

    private String repositoryPointcut;

    public String getRepositoryPointcut() {
        return repositoryPointcut;
    }

}