package org.coding.service;


import org.coding.core.FileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrepServiceTest {

    private GrepService grepService;
    private static Path path;

    @Mock
    private FileProcessor fileProcessor;

    @BeforeEach
    void setUp() throws URISyntaxException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchWhenFileProcessorReturnsSearchResults() {
        grepService = new GrepService("test-data", "addresses");
        List<String> result = grepService.search();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.get(0).equals("test-data/sample0.txt:13:Email addresses like test@example.com should be searchable."));
    }

    @Test
    void testSearchWhenFileProcessorDoesNotReturnSearchResultsForNonTextFile() {
        grepService = new GrepService("test-data", "unique");
        List<String> result = grepService.search();
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchWhenFileProcessorReturnsEmptyList() {
        grepService = new GrepService("test-data", "nonexistent");
        List<String> result = grepService.search();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGrepServiceWhenPathIsNullThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GrepService(null, "hello"));
        assertEquals("Root directory must not be null.", exception.getMessage());
    }

    @Test
    void testGrepServiceWhenSearchStringIsNullThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GrepService("test-data", null));
        assertEquals("Search string must not be null or blank.", exception.getMessage());
    }

    @Test
    void testGrepServiceWhenSearchStringIsEmptyThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GrepService("test-data", ""));
        assertEquals("Search string must not be null or blank.", exception.getMessage());
    }

    @Test
    void testGrepServiceWhenPathIsInvalidThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GrepService("foo", "hello"));
        assertEquals("Root directory does not exist!", exception.getMessage());
    }

    @Test
    void testGrepServiceWhenPathIsNotDirectoryThrowsIllegalArgumentException() throws IOException {
        File tempFile = File.createTempFile("test-file", ".txt");
        tempFile.deleteOnExit();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GrepService(tempFile.getAbsolutePath(), "hello"));
        assertEquals("Error: '"+tempFile.getAbsolutePath()+"' is not a directory.", exception.getMessage());
    }
}
