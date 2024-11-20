package utilities;
/**
 * MyDList.java
 *
 * @author Justin Nielsen
 * @version 1.2
 * 
 * Class Definition: This code is for editing a list. Adding/subtracting from the front and back of it
 * using references from MyDLLNode.java to complete these operations
 */


public class MyDLL<E> {
	//references to all the nodes created in MyDLLNode
    private MyDLLNode<E> head; 
    private MyDLLNode<E> tail; 
    private int size;       

    // Constructor to initialize the doubly linked list
    public MyDLL() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Returns the first node/head of list
    public MyDLLNode<E> getHead() {
    	//cant return nothing
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head;
    }

    // Adds a new node at the front of the list
    public void addFirst(E item) {
    	//creates
        MyDLLNode<E> newNode = new MyDLLNode<>(item);
        //list is empty, set head/tail to new node
        if (head == null) {  
        	head = tail = newNode;
        //head is not null, head is not put in the front
        } else {
            newNode.next = head;   
            head.prev = newNode;   
            head = newNode;        
        }
        //increase size of list
        size++; 
    }

    // Adds a new node at the end of the list
    public void addLast(E item) {
    	//creates
        MyDLLNode<E> newNode = new MyDLLNode<>(item);
        //if list is empty make head/tail be the new node
        if (tail == null) {  
            head = tail = newNode;
        //else put tail on the back of the list
        } else {
            newNode.prev = tail;  
            tail.next = newNode;  
            tail = newNode;     
        }
        //make list size bigger
        size++;  
    }

    // Removes the first element of the list and returns it
    public E removeFirst() {
    	//cant remove if there is nothing there
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        //element to be returned
        E removedElement = head.element;
        //Checking if there is just ONE node
        if (head == tail) { 
            head = tail = null;
        //move head to next node and prev to null "deleting"
        } else {
            head = head.next;  
            head.prev = null;  
        }
        //lower size of list
        size--;
        //return the previously stored element
        return removedElement;
    }

    // Removes last item in a list and returns it
    public E removeLast() {
    	//cant remove if its empty
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        //element to be returned
        E removedElement = tail.element; 
        //checking if there is just ONE node
        if (head == tail) { 
            head = tail = null;
        //if there is remove the last by setting it to null
        } else {
            tail = tail.prev;  
            tail.next = null;
        }
        //lower size of list
        size--; 
        //return ealier saved element
        return removedElement;
    }

    // Returns the size of the list
    public int size() {
        return size;
    }

    // Checks if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
