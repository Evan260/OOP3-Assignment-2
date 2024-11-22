package implementations;

import java.io.*;
import java.util.*;

/**
 * XMLParser.java
 * 
 * @author Team Riju
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
    private Stack<String> stack;  // The stack
    private Queue<String> errorQ;  // A queue to hold errors
    private Queue<String> extrasQ;  // A queue for anything else

    public XMLParser() {
    	// Set the variables to their respective type
        stack = new Stack<>();
        errorQ = new LinkedList<>();
        extrasQ = new LinkedList<>();
    }

    // Method to read XML file
    public List<String> readXML(String filePath) throws IOException {
        List<String> xmlTags = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        
        // Check all content in the reader
        while ((line = reader.readLine()) != null) {
            xmlTags.add(line.trim());
        }
        
        reader.close();
        return xmlTags;
    }
    // Method to parse following kitty's structure
    public void parse(List<String> xmlTags) {
        for (String tag : xmlTags) {
        
            if (isSelfClosingTag(tag)) {
                continue;
            }
            else if (isStartTag(tag)) {
                stack.push(tag);
            }
             else if (isEndTag(tag)) {
            	// If matches top, pop
                if (!stack.isEmpty() && stack.peek().equals(tag)) {
                    stack.pop();
                } 
                // If matches head of errorq, dequeue
                else if (!errorQ.isEmpty() && errorQ.peek().equals(tag)) {
                    errorQ.poll();
                } 
                // If stack is empty, add to errorq
                else if (stack.isEmpty()) {
                    errorQ.offer(tag);
                }  
                // Else search for matching start tag
                 else {
                    boolean foundMatch = false;
                    // Find match for end tag
                    while (!stack.isEmpty()) {
                    	// If top of stack the end tag, yes = found and break
                        if (stack.peek().equals(tag)) {
                            foundMatch = true;
                            break;
                        }
                        // Did not find a match so pop it off into extras
                        else {
                            extrasQ.offer(stack.pop());
                        }
                    }

                    if (foundMatch) {
                        // Pop each element from stack into errorQ until match
                        while (!stack.isEmpty() && !stack.peek().equals(tag)) {
                            errorQ.offer(stack.pop());
                        }
                        // Pop the matching start tag from stack
                        stack.pop();
                    } else {
                        // No match found, add to extrasQ
                        extrasQ.offer(tag);
                    }
                }
            }
        }

        // Deals with what is left over
        handleRemaining();
    }
    
    // Deal with any remaining tags left over after the parse
    private void handleRemaining() {
        // If stack is not empty, pop each element into errorQ
        while (!stack.isEmpty()) {
            errorQ.offer(stack.pop());
        }

        // If either queue or extras is empty report each element
        if (errorQ.isEmpty() && !extrasQ.isEmpty()) {
            while (!extrasQ.isEmpty()) {
                System.out.println("Error: Extra element " + extrasQ.poll());
            }
        }
        else if (!extrasQ.isEmpty() && errorQ.isEmpty()) {
            while (!errorQ.isEmpty()) {
                System.out.println("Error: Missing start tag " + errorQ.poll());
            }
        }
        else {
            // Both queues are not empty, peek both
            while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
                if (!errorQ.peek().equals(extrasQ.peek())) {
                    // Dequeue from errorQ and report as error
                    System.out.println("Error: " + errorQ.poll());
                } else {
                    // Dequeue from both
                    errorQ.poll();
                    extrasQ.poll();
                }
            }
        }
    }

    private boolean isSelfClosingTag(String tag) {
        // Basic check for self-closing tag, like <tag/>
        return tag.endsWith("/>");
    }

    private boolean isStartTag(String tag) {
        // Check if it's a start tag, like <tag>
        return tag.startsWith("<") && !tag.startsWith("</");
    }

    private boolean isEndTag(String tag) {
        // Check if it's an end tag, like </tag>
        return tag.startsWith("</");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java XMLParser <xml-file-path>");
            return;
        }

        try {
            String filePath = args[0];
            // Create an XMLParser instance
            XMLParser parser = new XMLParser();
            // Read XML tags from the file
            List<String> xmlTags = parser.readXML(filePath);
            // Parse the XML tags and handle errors
            parser.parse(xmlTags);
        } catch (IOException e) {
            System.out.println("Error reading the XML file: " + e.getMessage());
        }
    }
}