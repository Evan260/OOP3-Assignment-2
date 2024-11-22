package implementations;

/**
 * MyDLLNide.java
 * 
 * @author Team Riju
 * 
 * A generic node class for a Doubly Linked List (DLL).
 * Each node contains an element of type E and references to 
 * both the next and previous nodes in the list, allowing for
 * bidirectional traversal.
 *
 * @param <E> the type of element stored in the node
 */
public class MyDLLNode<E> {
	// creating the elements of the node. current, next, prev
	E element;
	MyDLLNode<E> next;
	MyDLLNode<E> prev;

	// Constructor to initialize the node with an element
	public MyDLLNode(E element) {
		this.element = element;
		this.next = null;
		this.prev = null;
	}

	// gets the elemenet
	public E getElement() {
		return element;
	}

	// gets the next node
	public MyDLLNode<E> getNext() {
		return next;
	}

	// gets the rprevious node
	public MyDLLNode<E> getPrev() {
		return prev;
	}
}