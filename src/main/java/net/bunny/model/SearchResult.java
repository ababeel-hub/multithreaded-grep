package net.bunny.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.file.Path;

/**
 * Represents a single search result from a file search operation.
 * Each {@code SearchResult} contains the file path, the line number, and the content of the line where a match was found.
 * {@code filePath} must not be {@code null}.
 * {@code lineNumber} must be greater than or equal to 1.
 * {@code lineContent} must not be {@code null}.
 * Throws {@link IllegalArgumentException} if any of the above conditions are violated.
 */
public class SearchResult {
    Path filePath;
    String lineContent;
    int lineNumber;

    public SearchResult(Path filePath,int lineNumber,  String lineContent) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        if (lineNumber < 1) {
            throw new IllegalArgumentException("Line number must be at least 1");
        }
        if (lineContent == null) {
            throw new IllegalArgumentException("Line content cannot be null");
        }
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.lineContent = lineContent;
    }

    /**
     * Returns the file path where the match was found.
     *
     * @return the file path; never {@code null}
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Returns the content of the matching line.
     *
     * @return the line content; never {@code null}
     */
    public String getLineContent() {
        return lineContent;
    }

    /**
     * Returns the line number where the match was found (1-based).
     *
     * @return the line number; always >= 1
     */
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        SearchResult that = (SearchResult) object;

        return new EqualsBuilder().append(lineNumber, that.lineNumber).append(filePath, that.filePath).append(lineContent, that.lineContent).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(filePath).append(lineContent).append(lineNumber).toHashCode();
    }

    @Override
    public String toString() {
        return filePath.toString() + ":" + lineNumber + ":" + lineContent;
    }
}