package utilities;

import java.io.*;
import java.util.*;

public class XMLParser {
	
	//the stack
    private Stack<String> stack; 
    //a queue to hold errors
    private Queue<String> errorQ;
    //a queue for anything else
    private Queue<String> extrasQ;

    public XMLParser() {
    	//setting all the previous variables to their respective type
        stack = new Stack<>();
        errorQ = new LinkedList<>();
        extrasQ = new LinkedList<>();
    }

    //method to read XML file
    public List<String> readXML(String filePath) throws IOException {
    	//create a arraylist to hold the xml tags
        List<String> xmlTags = new ArrayList<>();
        //create a new reader
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        //checks for all content in the reader
        while ((line = reader.readLine()) != null) {
            xmlTags.add(line.trim());
        }
        //close reader and return xmltags
        reader.close();
        return xmlTags;
    }
    //method to parse following kitty's structure
    public void parse(List<String> xmlTags) {
    	//for every tag in xmltags
        for (String tag : xmlTags) {
        	//if selfclosing ignore
            if (isSelfClosingTag(tag)) {
                continue;
            //if starttag push on stack
            } else if (isStartTag(tag)) {
                stack.push(tag);
            //if endtag 
            
            
            } else if (isEndTag(tag)) {
            	//if matches top, pop
                if (!stack.isEmpty() && stack.peek().equals(tag)) {
                    stack.pop();
                    
                //if matches head of errorq, dequeue
                } else if (!errorQ.isEmpty() && errorQ.peek().equals(tag)) {
                    errorQ.poll();
                    
                //if stack is empty, add to errorq
                } else if (stack.isEmpty()) {
                    errorQ.offer(tag);
                    
                //else search for matching start tag
                } else {
                    boolean foundMatch = false;
                    //find match for end tag
                    while (!stack.isEmpty()) {
                    	//is top of stack the end tag, yes = found and break
                        if (stack.peek().equals(tag)) {
                            foundMatch = true;
                            break;
                        //did not find a match so pop it off into extras
                        } else {
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

        //deals with what is left over
        handleRemaining();
    }
    
    //deals with any remaining tags leftover after the parse
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
        } else if (!extrasQ.isEmpty() && errorQ.isEmpty()) {
            while (!errorQ.isEmpty()) {
                System.out.println("Error: Missing start tag " + errorQ.poll());
            }
        } else {
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
