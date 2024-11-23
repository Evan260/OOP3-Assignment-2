package implementations;

import java.io.IOException;
import java.util.List;

/**
 * XMLParser.java
 * 
 * A XML parser implementation that validates XML tag structure and reports errors.
 * This parser uses a stack-based approach to match opening and closing XML tags,
 * and implements error detection for common XML structural issues. It processes
 * XML files line by line and validates tag pairs while handling special cases
 * like self-closing tags.
 * 
 * The parser has three data structures:
 * - A stack for tracking opening tags
 * - An error queue for collecting mismatched or problematic tags
 * - An extras queue for handling orphaned or unmatched tags
 * 
 * The implementation can detect and report various XML structural errors including:
 * - Missing start tags
 * - Missing end tags
 * - Mismatched tag pairs
 * - Extra tags
 * - Improperly nested tags
 *
 * @param <E> The type of elements held in the internal data structures.
 */
public class XMLParser {
    private MyStack<String> stack;      // The stack for tracking opening tags
    private MyQueue<String> errorQ;     // A queue to hold errors
    private MyQueue<String> extrasQ;    // A queue for any extra tags

    public XMLParser() {
        stack = new MyStack<>();
        errorQ = new MyQueue<>();
        extrasQ = new MyQueue<>();
    }

    /**
     * Reads an XML file and returns a list of trimmed lines.
     *
     * @param filePath The path to the XML file.
     * @return A list of trimmed XML lines.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> readXML(String filePath) throws IOException {
        List<String> xmlTags = new MyArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            xmlTags.add(line.trim());
        }

        reader.close();
        return xmlTags;
    }

    /**
     * Parses the list of XML tags and identifies structural errors.
     *
     * @param xmlTags The list of XML tags to parse.
     */
    public void parse(List<String> xmlTags) {
        int lineNumber = 0;
        boolean hasErrors = false;

        for (String tag : xmlTags) {
            lineNumber++;
            if (isSelfClosingTag(tag)) {
                continue; // Self-closing tags require no action
            } else if (isStartTag(tag)) {
                String tagName = extractTagName(tag);
                if (!tagName.isEmpty()) {
                    stack.push(tagName);
                } else {
                    errorQ.enqueue("Line " + lineNumber + ": Invalid start tag format: " + tag);
                    hasErrors = true;
                }
            } else if (isEndTag(tag)) {
                String tagName = extractTagName(tag);
                if (tagName.isEmpty()) {
                    errorQ.enqueue("Line " + lineNumber + ": Invalid end tag format: " + tag);
                    hasErrors = true;
                    continue;
                }

                if (stack.isEmpty()) {
                    errorQ.enqueue("Line " + lineNumber + ": No matching start tag for " + tag);
                    hasErrors = true;
                } else {
                    String lastTag = stack.peek();
                    if (lastTag.equals(tagName)) {
                        stack.pop();
                    } else {
                        errorQ.enqueue("Line " + lineNumber + ": Mismatched tag. Expected </" + lastTag + ">, found </" + tagName + ">");
                        hasErrors = true;
                        // Attempt to find the matching start tag
                        while (!stack.isEmpty() && !stack.peek().equals(tagName)) {
                            extrasQ.enqueue(stack.pop());
                        }
                        if (!stack.isEmpty()) {
                            stack.pop(); // Pop the matching tag
                        } else {
                            errorQ.enqueue("Line " + lineNumber + ": No matching start tag found for " + tag);
                        }
                    }
                }
            } else {
                // Handle invalid tag formats
                errorQ.enqueue("Line " + lineNumber + ": Invalid tag format: " + tag);
                hasErrors = true;
            }
        }

        // Handle any remaining tags in the stack
        handleRemaining();

        // Display results
        if (hasErrors || !errorQ.isEmpty() || !extrasQ.isEmpty()) {
            System.out.println("XML parsing completed with errors:");
            while (!errorQ.isEmpty()) {
                System.out.println(errorQ.dequeue());
            }
            while (!extrasQ.isEmpty()) {
                System.out.println("Extra tag: " + extrasQ.dequeue());
            }
        } else {
            System.out.println("XML document is well-formed.");
        }
    }

    /**
     * Handles any remaining tags after parsing.
     */
    private void handleRemaining() {
        while (!stack.isEmpty()) {
            String unclosedTag = stack.pop();
            errorQ.enqueue("Unclosed tag: <" + unclosedTag + ">");
        }
    }

    /**
     * Determines if the tag is a self-closing tag.
     *
     * @param tag The XML tag to check.
     * @return True if it's a self-closing tag; otherwise, false.
     */
    private boolean isSelfClosingTag(String tag) {
        return tag.matches("<\\w+\\s*/>");
    }

    /**
     * Determines if the tag is a start tag.
     *
     * @param tag The XML tag to check.
     * @return True if it's a start tag; otherwise, false.
     */
    private boolean isStartTag(String tag) {
        return tag.matches("<\\w+>");
    }

    /**
     * Determines if the tag is an end tag.
     *
     * @param tag The XML tag to check.
     * @return True if it's an end tag; otherwise, false.
     */
    private boolean isEndTag(String tag) {
        return tag.matches("</\\w+>");
    }

    /**
     * Extracts the tag name from a start or end tag.
     *
     * @param tag The XML tag.
     * @return The tag name, or an empty string if extraction fails.
     */
    private String extractTagName(String tag) {
        if (isStartTag(tag) || isSelfClosingTag(tag)) {
            // Remove '<' and '>' or '/>' to get the tag name
            return tag.replaceAll("<(/)?(\\w+).*?>", "$2");
        } else if (isEndTag(tag)) {
            // Remove '</' and '>' to get the tag name
            return tag.replaceAll("</(\\w+)>", "$1");
        }
        return "";
    }

    /**
     * Displays usage instructions.
     */
    private static void printUsage() {
        String usage = "Usage:\n" +
                       "  java XMLParser --help\n" +
                       "  java XMLParser --version\n" +
                       "  java XMLParser --file <filename>\n\n" +
                       "Options:\n" +
                       "  -h, --help        Show this help message.\n" +
                       "  -v, --version     Show application version.\n" +
                       "  -f, --file        Specify the XML file to parse.\n";
        System.out.println(usage);
    }

    /**
     * Displays version information.
     */
    private static void printVersion() {
        String version = "XMLParser Version 1.0";
        System.out.println(version);
    }

    /**
     * The main method parses command-line arguments and invokes the XML parser accordingly.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // No arguments provided
        if (args.length == 0) {
            printUsage();
            return;
        }

        // Flags to track options
        boolean showHelp = false;
        boolean showVersion = false;
        String filename = null;

        // Manual parsing of command-line arguments
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "--help":
                case "-h":
                    showHelp = true;
                    break;

                case "--version":
                case "-v":
                    showVersion = true;
                    break;

                case "--file":
                case "-f":
                    if (i + 1 < args.length) {
                        filename = args[++i];
                    } else {
                        System.err.println("Error: Missing filename after " + arg);
                        printUsage();
                        System.exit(1);
                    }
                    break;

                default:
                    System.err.println("Unknown option: " + arg);
                    printUsage();
                    System.exit(1);
            }
        }

        // Handle help option
        if (showHelp) {
            printUsage();
            System.exit(0);
        }

        // Handle version option
        if (showVersion) {
            printVersion();
            System.exit(0);
        }

        // Handle file parsing
        if (filename != null) {
            try {
                XMLParser parser = new XMLParser();
                List<String> xmlTags = parser.readXML(filename);
                parser.parse(xmlTags);
            } catch (IOException e) {
                System.err.println("Error reading the XML file: " + e.getMessage());
                System.exit(1);
            } catch (Exception e) {
                System.err.println("An error occurred during parsing: " + e.getMessage());
                System.exit(1);
            }
        } else {
            System.err.println("Error: No file specified.");
            printUsage();
            System.exit(1);
        }
    }
}
