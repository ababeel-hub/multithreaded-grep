package net.bunny.core;

/**
 * Matches string in the given input line.
 *  Also, the match is case-insensitive.
 * It returns false for null or empty input line.
 */
public class StringPatternMatcher implements PatternMatcher {
    private final String searchString;

    public StringPatternMatcher(String searchString) {
        this.searchString = searchString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(String line) {
        if (line == null || line.isBlank() || searchString == null || searchString.isBlank()) {
            return false;
        }
        return line.toLowerCase().contains(searchString.toLowerCase());
    }
}
