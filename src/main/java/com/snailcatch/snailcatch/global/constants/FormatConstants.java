package com.snailcatch.snailcatch.global.constants;

/**
 * Constants used for formatting output strings, such as logs or SQL explain results.
 */
public class FormatConstants {

    private FormatConstants() {
        // Prevent instantiation
    }

    /**
     * Hyphen character, often used for visual separators or placeholders.
     */
    public static final String HYPHEN = "-";

    /**
     * Pipe character ("|"), commonly used in table formatting.
     */
    public static final String PIPE = "|";

    /**
     * Pipe character with a trailing space ("| "), used to improve table readability.
     */
    public static final String PIPE_WITH_SPACE = "| ";

    /**
     * Left-justified format specifier prefix for use in {@code String.format} calls (e.g., "%-10s").
     */
    public static final String LEFT_JUSTIFY_FORMAT = "%-";

    /**
     * Format suffix used with {@code LEFT_JUSTIFY_FORMAT} to align table columns with a pipe and space (e.g., "%-10s | ").
     */
    public static final String FORMAT_STRING_WITH_PIPE = "s | ";

    /**
     * New line character, used to separate lines in formatted output.
     */
    public static final String NEW_LINE = "\n";

    /**
     * Dot character, typically used for package names or property key paths.
     */
    public static final String DOT = ".";
}
