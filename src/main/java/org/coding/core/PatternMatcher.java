package org.coding.core;

/**
 * Interface for matching a pattern within a line of text.
 * The input {@code line} may be {@code null} or empty.
 * If {@code line} is {@code null} or empty, implementations should return {@code false}.
 */
public interface PatternMatcher {
    /**
     * Checks if the pattern exists in the given line.
     * @param line the line of text to check; may be {@code null} or empty
     * @return{@code true} if the pattern exists in the line, {@code false} otherwise
     */
    boolean match(String line);
}
