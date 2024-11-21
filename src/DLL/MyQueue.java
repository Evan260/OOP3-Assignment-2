package DLL;

import implementations.Iterator;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * <p>
 * The MyQueue class provides a queue implementation using a doubly-linked list (MyDLL) as the underlying storage.
 * It supports standard queue operations such as enqueue, dequeue, peek, and others.
 * </p>
 * 
 * @param <E> The type of elements held in this queue.
 */
public class MyQueue<E> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private MyDLL<E> list; // Underlying doubly-linked list to store queue elements

    /**
     * Constructs an empty queue.
     */
    public MyQueue() {
        list = new MyDLL<>();
    }

    /**
     * Adds the specified element to the end of this queue.
     * 
     * @param element The element to add.
     * @throws NullPointerException If the specified element is null and the list does not support null elements.
     */
    public void enqueue(E element) throws NullPointerException {
        list.add(element);
    }

    /**
     * Retrieves and removes the head of this queue.
     * 
     * @return The head of this queue.
     * @throws NoSuchElementException If this queue is empty.
     */
    public E dequeue() throws NoSuchElementException {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.remove(0); // Remove from head (index 0)
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     * 
     * @return The head of this queue.
     * @throws NoSuchElementException If this queue is empty.
     */
    public E peek() throws NoSuchElementException {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.get(0); // Peek at head (index 0)
    }

    /**
     * Returns the number of elements in this queue.
     * 
     * @return The number of elements in this queue.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns true if this queue contains no elements.
     * 
     * @return true if this queue is empty; false otherwise.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Removes all of the elements from this queue. The queue will be empty after this call returns.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     * 
     * @return An iterator over the elements in this queue.
     */
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /**
     * Returns an array containing all of the elements in this queue in proper sequence.
     * 
     * @param toHold The array into which the elements of this queue are to be stored, if it is big enough;
     *               otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return An array containing all of the elements in this queue.
     * @throws NullPointerException If the specified array is null.
     */
    public E[] toArray(E[] toHold) throws NullPointerException {
        return list.toArray(toHold);
    }

    /**
     * Returns an array containing all of the elements in this queue in proper sequence.
     * 
     * @return An array containing all of the elements in this queue.
     */
    public Object[] toArray() {
        return list.toArray();
    }
}
