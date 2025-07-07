package org.coding.service;

import org.coding.core.FileProcessor;
import org.coding.core.PatternMatcher;
import org.coding.core.SequentialFileProcessor;
import org.coding.core.StringPatternMatcher;
import org.coding.model.SearchResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for performing recursive, multithreaded search for a text pattern within files in a directory tree.
 *
 * {@code GrepService} searches all regular files of {@ALLOWED_TYPES} under a specified root directory,
 * using a thread pool for parallel processing of all the files.
 *
 * The service can be used without specifying the threadPoolSize, in which it is {@100} by default.
 *
 * The search is case-insensitive.
 * The pattern matching is a String pattern matchers and only supports plain-text search (no regex support or no escape sequence support for now).
 *
 * The root directory must exist and be a directory.
 * The search string must not be {@code null} or blank.
 *
 * Throws {@link IllegalArgumentException} if arguments are invalid.
 * Returns a list of formatted result strings, one for each match found.
 */
public class GrepService {
    private final List<String> ALLOWED_TYPES = List.of(".txt");
    private final int THREAD_TERMINATION_TIMEOUT = 60;
    private final int threadPoolSize;
    private final String searchString;
    private final Path rootDirectoryPath;
    private final FileProcessor fileProcessor;
    private final PatternMatcher patternMatcher;

    public GrepService(String rootDirectory, String searchString) {
        this(100, rootDirectory, searchString);
    }

    public GrepService(int threadPoolSize, String rootDirectory, String searchString) {
        validateSearchString(searchString);
        this.rootDirectoryPath = getRootDirectoryPath(rootDirectory);
        this.searchString = searchString;
        this.threadPoolSize = threadPoolSize;
        this.fileProcessor = new SequentialFileProcessor();
        this.patternMatcher = new StringPatternMatcher(searchString);
    }

    /**
     * Performs a recursive, multithreaded search for the pattern in all allowed files under the root directory.
     *
     * @return a list of formatted result strings, one for each match found; never {@code null}
     * @throws RuntimeException if an I/O error occurs during the search
     * @return a list of search result strings formatted as "filename:line_number:line"
     */
    public List<String> search() {
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        try (Stream<Path> filePathStream = Files.walk(rootDirectoryPath).filter(Files::isRegularFile).filter(this::isValidFileType)) {
            List<Future<List<SearchResult>>> futures = filePathStream
                    .map(file -> executor.submit(() -> fileProcessor.processFile(file, patternMatcher)))
                    .toList();

            return futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.err.println("Warning: Error processing file: " + e.getMessage());
                            return new ArrayList<SearchResult>();
                        }
                    })
                    .flatMap(List::stream)
                    .map(SearchResult::toString)
                    .collect(Collectors.toList());

        } catch (IOException|IllegalArgumentException e) {
            throw new RuntimeException("Error while searching for string "+searchString+" in directory: " + rootDirectoryPath.toString(), e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(THREAD_TERMINATION_TIMEOUT, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Validates the search string argument for the grep operation.
     *
     * @param searchString the search string to validate; may be {@code null}
     * @throws IllegalArgumentException if {@code searchString} is {@code null} or blank
     */
    private void validateSearchString(String searchString) {
        if(searchString == null || searchString.isBlank()) {
            throw new IllegalArgumentException("Search string must not be null or blank.");
        }
    }

    /**
     * Validates the root directory argument and returns its {@link Path} representation.
     *
     * @param rootDirectory the path to the root directory as a string, may be {@code null}
     * @return the {@link Path} object representing the root directory
     * @throws IllegalArgumentException if {@code rootDirectory} is {@code null}, does not exist, or is not a directory
     */
    private Path getRootDirectoryPath(String rootDirectory) {
        if(rootDirectory == null) {
            throw new IllegalArgumentException("Root directory must not be null.");
        }

        Path rootPath = Paths.get(rootDirectory);
        if (!rootPath.toFile().exists()) {
            throw new IllegalArgumentException("Root directory does not exist!");
        }
        if (!rootPath.toFile().isDirectory()) {
            throw new IllegalArgumentException("Error: '" + rootDirectory + "' is not a directory.");
        }
        return rootPath;
    }

    /** Checks if the file type is one that is allowed as per ALLOWED_TYPES list.
     *
     * @param filePath the file to be processed
     * @return {@true} if the file type, i.e. ends with a suffix that is in ALLOWED_TYPE list.
     */
    private boolean isValidFileType(Path filePath) {
        String fileName = filePath.getFileName().toString();
        return ALLOWED_TYPES.stream().anyMatch(fileName.toLowerCase()::endsWith);
    }
}