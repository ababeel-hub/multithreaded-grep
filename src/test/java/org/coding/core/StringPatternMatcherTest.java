package org.coding.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringPatternMatcherTest {
    private StringPatternMatcher stringPatternMatcher;

    @Test
    void testStringMatcherIsCaseInsensitive() {
        stringPatternMatcher = new StringPatternMatcher("hello");
        List<String> validMatches = Arrays.asList("HELLO", "hello", "heLLo");
        validMatches.stream().forEach(test -> {
            assertTrue(stringPatternMatcher.match(test));
        });
    }

    @Test
    void testStringMatcherCanMatchWithTrailingSpaces() {
        stringPatternMatcher = new StringPatternMatcher("hello   ");
        assertFalse(stringPatternMatcher.match("this string has whitespace, hello world"));
        assertTrue(stringPatternMatcher.match("this string has whitespace, hello   world"));
    }

    @Test
    void testStringMatcherCanMatchWhitespace() {
        stringPatternMatcher = new StringPatternMatcher("hello\sworld");
        assertTrue(stringPatternMatcher.match("this line has whitespace, hello world"));
    }

    @Test
    void testStringMatcherCanMatchTab() {
        stringPatternMatcher = new StringPatternMatcher("hello\tworld");
        assertTrue(stringPatternMatcher.match("this line has tab, hello\tworld"));
    }

    @Test
    void testStringMatcherCanMatchDoubleSlashes() {
        stringPatternMatcher = new StringPatternMatcher("hello\\world");
        assertTrue(stringPatternMatcher.match("this line has tab, hello\\world"));
    }

    @Test
    void testStringMatcherCanMatchDoubleQuotes() {
        stringPatternMatcher = new StringPatternMatcher("hello\"world");
        assertTrue(stringPatternMatcher.match("this line has tab, hello\"world"));
    }

    @Test
    void testStringMatcherCanMatchSingleQuotes() {
        stringPatternMatcher = new StringPatternMatcher("hello\'world");
        assertTrue(stringPatternMatcher.match("this line has tab, hello'world"));
    }

    @Test
    void testStringMatcherWithNullSearchStringReturnsNull() {
        stringPatternMatcher = new StringPatternMatcher(null);
        assertFalse(stringPatternMatcher.match("this line has tab, hello world"));
    }

    @Test
    void testStringMatcherWithEmptySearchStringReturnsFalse() {
        stringPatternMatcher = new StringPatternMatcher("");
        assertFalse(stringPatternMatcher.match("this line has tab, hello world"));
    }

    @Test
    void testStringMatcherWithEmptyLineReturnsFalse() {
        stringPatternMatcher = new StringPatternMatcher("hello");
        assertFalse(stringPatternMatcher.match(""));
    }

    @Test
    void testStringMatcherWithNullLineReturnsFalse() {
        stringPatternMatcher = new StringPatternMatcher("hello");
        assertFalse(stringPatternMatcher.match(null));
    }
}
