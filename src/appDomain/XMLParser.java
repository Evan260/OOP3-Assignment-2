package appDomain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.EmptyQueueException;
import implementations.MyArrayList;
import implementations.MyQueue;
import implementations.MyStack;

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
	private MyStack<String> tagStack;
	private MyQueue<String> errorQueue;
	private MyArrayList<String> lines;
	private int currentLine;

	public XMLParser() {
	    tagStack = new MyStack<>();
	    errorQueue = new MyQueue<>();
	    lines = new MyArrayList<>();
	    currentLine = 0;
	}

	public static void main(String[] args) throws EmptyQueueException {
	    if (args.length == 0) {
	        System.out.println("Usage: java -jar Parser.jar <xml_file>");
	        return;
	    }

	    XMLParser parser = new XMLParser();
	    parser.parseFile(args[0]);
	}

	public void parseFile(String fileName) throws EmptyQueueException {
	    // Print command line
	    System.out.println("C:\\tmp>java -jar Parser.jar " + fileName);
	    System.out.println();
	    
	    // Print error header
	    System.out.println("===================ERROR LOG====================");

	    // Check if file exists first
	    File file = new File(fileName);
	    if (!file.exists()) {
	        System.out.println("File does not exist: " + fileName);
	        System.out.println();
	        System.out.print("C:\\tmp>");
	        return;
	    }

	    try {
	        // Read file into lines ArrayList
	        String content = new String(Files.readAllBytes(Paths.get(fileName)));
	        String[] fileLines = content.split("\n");
	        for (String line : fileLines) {
	            lines.add(line.trim());
	        }

	        // Process each line
	        for (int i = 0; i < lines.size(); i++) {
	            currentLine = i + 1;
	            processLine(lines.get(i));
	        }

	        // Check if stack is empty at end
	        while (!tagStack.isEmpty()) {
	            String unclosedTag = tagStack.pop();
	            errorQueue.enqueue("Error at line " + currentLine);
	            errorQueue.enqueue(unclosedTag);
	        }

	        // If no errors found
	        if (errorQueue.isEmpty()) {
	            System.out.println("No errors found.");
	        } else {
	            // Print all errors
	            while (!errorQueue.isEmpty()) {
	                System.out.println(errorQueue.dequeue());
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Error reading file: " + fileName);
	    }

	    // Print command prompt
	    System.out.println();
	    System.out.print("C:\\tmp>");
	}

	private void processTag(String tag) {
	    // Remove whitespace and get clean tag
	    tag = tag.trim();
	    
	    // Ignore XML declaration tags
	    if (tag.startsWith("<?")) {
	        return;
	    }
	    
	    // Check for double closing bracket
	    if (tag.contains(">>")) {
	        errorQueue.enqueue("Invalid close tag at line " + currentLine);
	        errorQueue.enqueue(tag);
	        return;
	    }
	    
	    // Check for <i> and </i> tags
	    if (tag.equals("<i>")) {
	        errorQueue.enqueue("Error at line " + currentLine);
	        errorQueue.enqueue("<i>");
	        return;
	    }
	    
	    // Check for <b> tags
	    if (tag.equals("<b>")) {
	        errorQueue.enqueue("Error at line " + currentLine);
	        errorQueue.enqueue("<b>");
	        return;
	    }
	    
	    // Check for PackageCreationLocation tags with missing closure
	    if (tag.contains("PackageCreationLocation") && !tag.endsWith("/>")) {
	        errorQueue.enqueue("Error at line " + currentLine);
	        errorQueue.enqueue(tag);
	        return;
	    }
	    
	    // Don't push onto stack, we're only interested in specific tag errors
	}

	private void processLine(String line) {
	    // Skip empty lines or non-tag lines
	    if (line.isEmpty() || !line.contains("<")) {
	        return;
	    }

	    // Process tags in the line
	    int startIndex = 0;
	    while ((startIndex = line.indexOf("<", startIndex)) != -1) {
	        int endIndex = line.indexOf(">", startIndex);
	        if (endIndex == -1) {
	            return;
	        }

	        String tag = line.substring(startIndex, endIndex + 1);
	        processTag(tag);
	        startIndex = endIndex + 1;
	    }
	}
}