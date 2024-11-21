# XML Parser Project

A Java implementation of an XML document parser using custom data structures. This project includes implementations of Stack, Queue, ArrayList, and Doubly Linked List ADTs to validate XML document syntax.

This project is part of SAIT's Object-Oriented Programming 3 (CRPG 304) course.

## Features

- Custom data structure implementations:
  - ArrayList (MyArrayList)
  - Doubly Linked List (MyDLL)
  - Stack (MyStack)
  - Queue (MyQueue)
- XML validation capabilities:
  - Opening/closing tag matching
  - Self-closing tag validation
  - Root tag verification
  - Case-sensitive tag matching
  - Proper nesting validation

## Requirements

- Java 8 
- JUnit 4
- Eclipse IDE

## Installation

1. Clone this repository
2. Import the project into Eclipse:
   - File > Import > General > Existing Projects into Workspace
   - Select the root directory
   - Click Finish

## Usage

```bash
java -jar Parser.jar input.xml
```

The parser will analyze the XML file and output any syntax errors found, including:
- Missing closing tags
- Mismatched tag pairs
- Improper nesting
- Multiple root elements

## Project Structure

```
src/
├── ADT
│   ├── ListADT.java
│   ├── QueueADT.java
│   ├── XMLParser.java
│   └──StackADT.java
│── DLL
│   ├── MyDLL.java
│   └── MyDLLNode.java
│── exceptions/
│   └── EmptyQueueException.java
└── Implementations
    ├── Iterator.java
    ├── MyArrayList.java
    └── MyStack.java

```

## Implementation Details

### MyDLL (Doubly Linked List)
- Bidirectional node traversal
- Dynamic size management
- O(1) operations for add/remove at ends

### XML Parser
- Utilizes custom Stack implementation for tag matching
- Validates XML syntax according to basic XML rules
- Reports errors in order of occurrence

## Testing

Run the included JUnit tests to verify data structure implementations:

```bash
cd project_directory
java org.junit.runner.JUnitCore utilities.MyDLLTest
```
