package org.coding;

import org.coding.service.GrepService;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        if (args.length == 1 && (
                        "--help".equals(args[0]) ||
                        "-help".equals(args[0]) ||
                        "-h".equals(args[0])
        )) {
            printToolUsage();
            System.exit(0);
        }

        if (args.length < 2) {
            printToolUsage();
            System.exit(1);
        } else {
            String rootDir = args[0];
            String pattern = args[1];
            try {
                GrepService grepService = new GrepService(rootDir, pattern);
                List<String> results = grepService.search();
                if (results.isEmpty()) {
                    System.out.println("No matches found for pattern: " + pattern);
                } else {
                    System.out.println("Found match(es) in : " + results.size()+" lines");
                    results.forEach(System.out::println);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    private static void printToolUsage() {
        System.out.println("Multithreaded Grep - Recursive File Search Tool");
        System.out.println("Usage: ./bin/multithreaded-grep <root_directory> <search_pattern>");
        System.out.println("  - Case-insensitive by default");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  root_directory   The directory to search recursively");
        System.out.println("  search_pattern   The text pattern to search for");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  ./bin/multithreaded-grep /path/to/search \"hello world\"");
        System.out.println();
        System.out.println("Need help? Usage: ./bin/multithreaded-grep -h | --help");
    }
}