package DLL;

import implementations.Iterator;
import ADT.ListADT;
import java.util.NoSuchElementException;

/**
 * <p>
 * The MyDLL class provides a doubly-linked list implementation of the ListADT
 * interface. It maintains references to both the head and tail of the list,
 * allowing for efficient operations at both ends of the list.
 * </p>
 * 
 * @param <E> The type of elements this list holds.
 */
public class MyDLL<E> implements ListADT<E> {
    private Node<E> head;    // Reference to the first node in the list
    private Node<E> tail;    // Reference to the last node in the list
    private int size;        // Current number of elements in the list

    /**
     * Private inner class representing a node in the doubly-linked list.
     * Each node contains the element and references to the next and previous nodes.
     */
    private static class Node<E> {
        E element;           // The element stored in this node
        Node<E> next;       // Reference to the next node
        Node<E> prev;       // Reference to the previous node

        /**
         * Constructs a new node with the given element.
         * 
         * @param element The element to store in this node.
         */
        Node(E element) {
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Constructs an empty list with no elements.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> newNode = new Node<>(toAdd);

        // Handle special cases for empty list or inserting at ends
        if (size == 0) {
            // Adding to empty list
            head = tail = newNode;
        } else if (index == 0) {
            // Adding at head
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            // Adding at tail
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            // Adding in middle of list
            Node<E> current = getNodeAt(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
        }

        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        return add(size, toAdd);  // Add to end of list
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null collection");
        }

        boolean modified = false;
        Iterator<? extends E> iterator = toAdd.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
            modified = true;
        }
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNodeAt(index).element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = getNodeAt(index);
        E element = current.element;

        // Handle special cases for single element or removing from ends
        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            // Remove from middle of list
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }

        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (toRemove.equals(current.element)) {
                return remove(index);
            }
            current = current.next;
            index++;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set null element");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> node = getNodeAt(index);
        E oldElement = node.element;
        node.element = toChange;
        return oldElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }

        Node<E> current = head;
        while (current != null) {
            if (toFind.equals(current.element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null");
        }

        // If array is too small, create new array of same type and sufficient size
        if (toHold.length < size) {
            toHold = (E[]) java.lang.reflect.Array.newInstance(
                    toHold.getClass().getComponentType(), size);
        }

        // Copy elements to array
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.element;
            current = current.next;
        }

        // Set first element after list to null if array is larger than list
        if (toHold.length > size) {
            toHold[size] = null;
        }

        return toHold;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.element;
            current = current.next;
        }
        return array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    /**
     * Helper method to get the node at a specific index.
     * Traverses from the closer end of the list for efficiency.
     * 
     * @param index The index of the desired node.
     * @return The node at the specified index.
     */
    private Node<E> getNodeAt(int index) {
        Node<E> current;
        if (index < size / 2) {
            // If index is in first half, traverse from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            // If index is in second half, traverse from tail
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * Private inner class that implements the Iterator interface.
     * Provides a way to iterate through the list without exposing the internal structure.
     */
    private class DLLIterator implements Iterator<E> {
        private Node<E> current = head;
        private Node<E> lastReturned = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            current = current.next;
            return lastReturned.element;
        }
    }
}