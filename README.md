# Multithreaded Grep

A high-performance, multithreaded, recursive file search tool for text patterns, similar to Unix `grep`, written in Java.

## Features
- **Recursive search** through directories
- **Multithreaded** for fast performance
- **Case-insensitive** matching by default
- **Only `.txt` files** are searched by default

> **Note:** The tool currently counts and displays matches per line, not per occurrence. If a word appears multiple times in the same line, it will be reported as a single match for that line, not as multiple matches.
>
> **Example:**
> If a line in the file is:
> ```
> Hello world! This line says hello again: hello.
> ```
> and you search for `hello`, this line will be counted as **one match**, even though `hello` appears three times in the line.
> And the output will look like:
> ```
> ./bin/multithreaded-grep test-data "HeLLo"
> Total match(es): 1
> test-data/sample0.txt:1:Hello world! This line says hello again: hello
> ```
---

## Out of Scope
- **Regex support:** The tool does not support regular expressions; only plain-text search is available.
- **Escape sequence support:** Patterns are matched as plain text; escape sequences like `\t`, `\n`, etc., are not interpreted.
- **Counting actual occurrences:** The tool does not count or display the number of times a pattern appears within a single line; it only reports per-line matches.

---

## Requirements
- Java 17 or higher
- Maven 3.x

---

## Setup

1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd multithreaded-grep
   ```

2. **Build the project with Maven:**
   ```sh
   mvn clean package
   ```
   This will generate the executable JAR at `target/multithreaded-grep-1.0-SNAPSHOT.jar`.

3. **(Optional) Make the bin tool executable:**
   ```sh
   chmod +x bin/multithreaded-grep
   ```

---

## Usage

### Command Line

Run the tool using the provided script:

```sh
./bin/multithreaded-grep <root_directory> <search_pattern>
```

#### Arguments
- `<root_directory>`: The directory to search recursively (e.g., `test-data`)
- `<search_pattern>`: The text pattern to search for (e.g., `hello world`)

#### Options
- `-h`, `--help`: Show usage instructions

#### Examples

Search for the phrase "hello world" in all `.txt` files under `test-data/`:
```sh
./bin/multithreaded-grep test-data "hello world"
```

Search for lines containing an email address:
```sh
./bin/multithreaded-grep test-data "test@example.com"
```

#### Sample Output
```
Total match(es): 2
test-data/sample0.txt:3:Hello world! This line contains the word "Hello".
test-data/sample1.txt:5:Double Quoted text: "hello world" in quotes
```

If no matches are found:
```
No matches found for pattern: <search_pattern>
```

---

## Development & Testing

- **Run all tests:**
  ```sh
  mvn test
  ```
- Test files are located in `src/test/java/` and sample data in `test-data/`.

---

## Project Structure

```
multithreaded-grep/
├── bin/                  # Executable script for running the tool
├── src/
│   ├── main/java/        # Application source code
│   └── test/java/        # Unit and integration tests
├── test-data/            # Sample .txt files for testing/demo
├── pom.xml               # Maven build file
└── README.md             # Project documentation
```

---
