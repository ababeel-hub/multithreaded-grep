package net.bunny.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SearchResultTest {

    private SearchResult searchResult;

    @Test
    void testSearchResultValid() {
        searchResult = new SearchResult(Path.of("test-data"), 4, "This is a test");
        assertAll("search result",
                () -> assertEquals("test-data", searchResult.getFilePath().toString()),
                () -> assertEquals(4, searchResult.getLineNumber()),
                () -> assertEquals("This is a test", searchResult.getLineContent()));
    }

    @Test
    void testSearchResultNullValueForFolder() {
        assertThrows(IllegalArgumentException.class, () -> new SearchResult(null, 4, "This is a test"));
    }

    @Test
    void testSearchResultInvalidValueForLineNumber() {
        assertThrows(IllegalArgumentException.class, () -> new SearchResult(Path.of("test-data"), -1, "This is a test"));
    }

    @Test
    void testSearchResultNullValueForLine() {
        assertThrows(IllegalArgumentException.class, () -> new SearchResult(Path.of("test-data"), 4, null));
    }
}
