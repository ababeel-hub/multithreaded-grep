package org.coding.core;

import org.coding.model.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of FileProcessor processes incoming files sequentially.
 * It reads the files and calls the pattern matcher for each line one at a time.
 */
public class SequentialFileProcessor implements FileProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchResult> processFile(Path filePath, PatternMatcher patternMatcher) {
        if (patternMatcher == null || filePath == null ) {
            System.err.println("Null argument: patternMatcher or filePath is missing in " + getClass().getSimpleName());
            return new ArrayList<>();
        }
        List<SearchResult> searchResults = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (patternMatcher.match(line)) {
                    searchResults.add(new SearchResult(filePath, lineNumber, line));
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not read file " + filePath + ": " + e.getMessage());
        }
        return searchResults;
    }
}
