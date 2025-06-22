package net.bunny.core;

import net.bunny.model.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SequentialFileProcessorTest {
    private SequentialFileProcessor sequentialFileProcessor;

    @Mock
    private PatternMatcher mockPatternMatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFileValidResultWhenPatternMatches() throws URISyntaxException {
        sequentialFileProcessor = new SequentialFileProcessor();

        when(mockPatternMatcher.match(anyString())).thenReturn(true);

        Path filePath = Paths.get(getClass().getClassLoader().getResource("unit-test/sample.txt").toURI());

        List<SearchResult> results = sequentialFileProcessor.processFile(filePath, mockPatternMatcher);
        assertFalse(results.isEmpty());
        assertEquals(11, results.size());
    }

    @Test
    void testProcessFileEmptyResultWhenPatternDoesNotMatch() throws URISyntaxException {
        sequentialFileProcessor = new SequentialFileProcessor();

        when(mockPatternMatcher.match(anyString())).thenReturn(false);

        Path path = Paths.get(getClass().getClassLoader().getResource("unit-test/sample.txt").toURI());

        List<SearchResult> results = sequentialFileProcessor.processFile(path, mockPatternMatcher);
        assertTrue(results.isEmpty());
    }

    @Test
    void testProcessFileNullFilePathReturnsEmptyList() {
        sequentialFileProcessor = new SequentialFileProcessor();
        assertTrue(sequentialFileProcessor.processFile(null, mockPatternMatcher).isEmpty());
    }

    @Test
    void testProcessFileNullPatternMatcherReturnsEmptyList() throws URISyntaxException {
        sequentialFileProcessor = new SequentialFileProcessor();
        Path path = Paths.get(getClass().getClassLoader().getResource("unit-test/sample.txt").toURI());
        assertTrue(sequentialFileProcessor.processFile(path, null).isEmpty());
    }

    @Test
    void testProcessFileWhenDirectoryIsPassedReturnsEmptyList() throws URISyntaxException {
        sequentialFileProcessor = new SequentialFileProcessor();
        when(mockPatternMatcher.match(anyString())).thenReturn(true);
        Path path = Paths.get(getClass().getClassLoader().getResource("unit-test").toURI());
        assertTrue(sequentialFileProcessor.processFile(path, mockPatternMatcher).isEmpty());
    }
}
