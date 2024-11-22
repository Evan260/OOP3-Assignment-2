package exceptions;

/**
 * EmptyQueueException.java
 *
 * @author Team Riju
 * 
 * A custom exception class that is thrown when attempting to dequeue from an empty queue.
 * This exception extends the standard Java Exception class.
 */
public class EmptyQueueException extends Exception {

	public EmptyQueueException() {
		super("Queue is empty. Cannot dequeue.");
	}
}