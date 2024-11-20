package utilities;
/**
 * MyNode.java
 *
 * @author Justin Nielsen
 * @version 1.2
 * 
 * Class Definition: This is MyNode, which has references to other bits of code,
 * the main use of MyNode is for MyDList to use for operations - these operations needing those references
 */

public class MyDLLNode<E> {
	//creating the elements of the node. current, next, prev
    E element;            
    MyNode<E> next;      
    MyNode<E> prev;       

    // Constructor to initialize the node with an element
    public MyNode(E element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }
    //gets the elemenet
    public E getElement() {
        return element;  
    }

    //gets the next node
    public MyNode<E> getNext() {
        return next;  
    }

    //gets the rprevious node
    public MyNode<E> getPrev() {
        return prev;  
    }
}