package com.snailcatch.snailcatch.global.constants;

/**
 * Application-wide constants used for configuration and package resolution.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    /**
     * The base package name of the application.
     * This is typically used for component scanning or reflection-based operations.
     */
    public static final String BASE_PACKAGE = "com.snailcatch.snailcatch";

    /**
     * Prefix used in application properties (e.g., application.yml or application.properties).
     * Useful for defining custom configuration properties with a unified namespace.
     */
    public static final String PROPERTY_PREFIX = "snail-catch";
}
