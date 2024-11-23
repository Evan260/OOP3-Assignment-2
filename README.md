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

## How to Run the Program

1. Right-click on `XMLParser.java` in the Project Explorer
2. Select `Run as` from the context menu
3. Click `Run configurations...`
4. Click the `Arguments` tab
5. In the "Program arguments" field, enter a command:
 
   ```
   res\sample1.xml
   ```
   Or
   ```
   res\sample2.xml
   ```
7. Click the `Run` button

### Notes
- Use Windows-style backslashes (`\`) in the file path
- Make sure to include the full path starting from the `res` directory
- The program accepts only one XML file as input at a time

## Project Structure

```
src/
├── appDomain/
│   └── XMLParser.java
├── exceptions/
│   └── EmptyQueueException.java
├── implementations/
│   ├── MyArrayList.java
│   ├── MyDLL.java
│   ├── MyDLLNode.java
│   ├── MyQueue.java
│   └── MyStack.java
├── utilities/
│   ├── Iterator.java
│   ├── ListADT.java
│   ├── QueueADT.java
│   └── StackADT.java
└── test/
    └── unitTests/
        ├── ArrayListTest.java
        ├── DLLTest.java
        ├── QueueTest.java
        └── StackTest.java
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

Run the included JUnit tests to verify data structure implementations
