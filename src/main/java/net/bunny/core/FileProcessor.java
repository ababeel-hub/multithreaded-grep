package net.bunny.core;

import net.bunny.model.SearchResult;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface for processing a given file to find all lines that match a given pattern.
 * It reads the file and uses the provided {@link PatternMatcher} to identify matching lines.
 */
public interface FileProcessor {

    /**
     * Processes the given file and returns a list of search results for lines matching the pattern.
     *
     * @param filePath       the path to the file to process; must not be {@code null}
     * @param patternMatcher the pattern matcher to use, must not be {@code null}
     *                       If either argument is invalid, the method should return an empty list and not throw exceptions.
     * @return a list of {@link SearchResult} objects for each matching line; never {@code null}
     */
    List<SearchResult> processFile(Path filePath, PatternMatcher patternMatcher);
}
