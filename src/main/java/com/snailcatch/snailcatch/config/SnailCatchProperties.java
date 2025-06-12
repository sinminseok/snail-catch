package com.snailcatch.snailcatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for SnailCatch.
 *
 * <p>
 * Binds external configuration properties prefixed with "snail-catch" into this class,
 * allowing easy access to configuration values within the application.
 * </p>
 *
 * <p>
 * Properties include:
 * <ul>
 *   <li><b>enabled</b> - Enables or disables the SnailCatch feature (default: true).</li>
 *   <li><b>repositoryPointcut</b> - Defines the AOP pointcut expression to intercept repository methods.</li>
 *   <li><b>apiKey</b> - API key used for authentication in log transmission or external communication.</li>
 * </ul>
 * </p>
 */
@ConfigurationProperties(prefix = "snail-catch")
public class SnailCatchProperties {

    /**
     * Flag indicating whether SnailCatch is enabled.
     * Defaults to true if not explicitly set.
     */
    private boolean enabled = true;

    /**
     * AOP pointcut expression to specify which repository methods
     * should be intercepted for slow query logging.
     */
    private String repositoryPointcut;

    /**
     * API key for authentication when sending logs or accessing external services.
     */
    private String apiKey;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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
