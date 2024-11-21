package implementations;

import DLL.MyDLL;
import ADT.QueueADT;
import exceptions.EmptyQueueException;
import java.util.Arrays;

/**
 * The MyQueue class provides a queue implementation using a doubly-linked list (MyDLL) as the underlying storage.
 * It implements the QueueADT interface, providing standard queue operations.
 *
 * @param <E> The type of elements held in this queue.
 */
public class MyQueue<E> implements QueueADT<E> {
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
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public void enqueue(E element) throws NullPointerException {
        list.add(element);
    }

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return The head of this queue.
     * @throws EmptyQueueException If this queue is empty.
     */
    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.remove(0);
    }

    /**
     * Removes all elements from this queue.
     */
    @Override
    public void dequeueAll() {
        list.clear();
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return The head of this queue.
     * @throws EmptyQueueException If this queue is empty.
     */
    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.get(0);
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return The number of elements in this queue.
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty; false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns true if this queue is full.
     * Since MyDLL is unbounded, this always returns false.
     *
     * @return false, as the queue is never full.
     */
    @Override
    public boolean isFull() {
        return false; // Unbounded queue
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     *
     * @return An iterator over the elements in this queue.
     */
    @Override
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
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        return list.toArray(toHold);
    }

    /**
     * Returns an array containing all of the elements in this queue in proper sequence.
     *
     * @return An array containing all of the elements in this queue.
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Returns true if this queue contains the specified element.
     *
     * @param toFind The element whose presence in this queue is to be tested.
     * @return true if this queue contains the specified element.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    /**
     * Searches for the specified element in this queue and returns its 1-based position.
     *
     * @param toFind The element to search for.
     * @return The 1-based position of the element if found; -1 otherwise.
     * @throws NullPointerException If the specified element is null.
     */
    @Override
    public int search(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        int index = 1;
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            E current = it.next();
            if (toFind.equals(current)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Compares the specified object with this queue for equality. Returns true if and only if
     * the specified object is also a queue, both queues have the same size, and all corresponding
     * pairs of elements in the two queues are equal.
     *
     * @param obj The object to be compared for equality with this queue.
     * @return true if the specified object is equal to this queue.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof QueueADT<?>))
            return false;
        QueueADT<?> other = (QueueADT<?>) obj;
        if (this.size() != other.size())
            return false;
        
        Object[] thisArray = this.toArray();
        Object[] otherArray = other.toArray();
        
        return Arrays.equals(thisArray, otherArray);
    }

	@Override
	public boolean equals(QueueADT<E> that) {
		// TODO Auto-generated method stub
		return false;
	}
}
