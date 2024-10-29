package utilities;
/**
 * <p>
 * The <code>MyQueue</code> interface represents a first-in-first-out (FIFO) queue of objects.
 * This interface defines all the basic operations that a queue should support.
 * Implementing classes must ensure proper queue behavior and handle edge cases appropriately.
 * </p>
 * 
 * @param <E> The type of elements this queue holds
 */
public interface MyQueue<E> {
    /**
     * Adds an element to the end of the queue.
     * 
     * @param element The element to be added to the queue
     * @return true if the element was successfully added
     * @throws NullPointerException if the specified element is null and the
     *         queue implementation does not support null elements
     */
    public boolean enqueue(E element);
    
    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return The element removed from the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public E dequeue();
    
    /**
     * Retrieves, but does not remove, the element at the front of the queue.
     * 
     * @return The element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public E first();
    
    /**
     * Checks if the queue is empty.
     * 
     * @return true if the queue contains no elements
     */
    public boolean isEmpty();
    
    /**
     * Returns the number of elements in the queue.
     * 
     * @return The number of elements in the queue
     */
    public int size();
    
    /**
     * Returns a string representation of the queue.
     * 
     * @return A string representation of the queue
     */
    public String toString();
}

