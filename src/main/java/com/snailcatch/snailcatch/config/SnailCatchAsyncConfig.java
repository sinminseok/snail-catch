package com.snailcatch.snailcatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration class to enable asynchronous method execution in the SnailCatch application.
 *
 * <p>By adding @EnableAsync, Spring's asynchronous method execution capability is activated,
 * allowing methods annotated with @Async to run in separate threads.</p>
 */
@Configuration
@EnableAsync
public class SnailCatchAsyncConfig {
}
