package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> elements;
    
    public MyStack() {
        elements = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot push null element onto stack");
        }
        elements.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public Object[] toArray() {
        // Create an array with the same size as elements
        Object[] array = new Object[elements.size()];

        // Loop through the elements in reverse order to match the expected order
        for (int i = 0; i < elements.size(); i++) {
            array[i] = elements.get(elements.size() - 1 - i); // Get elements in reverse order
        }

        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        // Check if the provided array is too small
        if (holder.length < elements.size()) {
            // If the array is too small, create a new array of the correct size
            holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), elements.size());
        }

        // Fill the provided array with the elements in the stack (or similar structure)
        for (int i = 0; i < elements.size(); i++) {
            holder[i] = elements.get(elements.size() - 1 - i);  // Reverse order
        }

        return holder;
    }


    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return elements.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        
        // Search from top of stack (end of list) to get 1-based position from top
        for (int i = elements.size() - 1; i >= 0; i--) {
            if (toFind.equals(elements.get(i))) {
                return elements.size() - i;  // Convert to 1-based position from top
            }
        }
        return -1;
    }
    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E> {
        private int index; // Start at the top of the stack (last added element)
        private final E[] iteratorElements;

        @SuppressWarnings("unchecked")
        public StackIterator() {
            // Create a copy of elements in reverse order
            iteratorElements = (E[]) new Object[size()];
            // Populate the array with the elements in LIFO order (from top to bottom)
            for (int i = 0; i < size(); i++) {
                iteratorElements[i] = elements.get(size() - 1 - i); // Reverse the order
            }
            this.index = 0; // Start the iteration from the top (last element added)
        }

        @Override
        public boolean hasNext() {
            return index < iteratorElements.length;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iteratorElements[index++]; // Return the next element in LIFO order
        }
    }


    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null) {
            return false;
        }
        
        if (this.size() != that.size()) {
            return false;
        }
        
        // Create iterators for both stacks
        Iterator<E> thisIter = this.iterator();
        Iterator<E> thatIter = that.iterator();
        
        // Compare elements in order
        while (thisIter.hasNext()) {
            E thisElement = thisIter.next();
            E thatElement = thatIter.next();
            
            if (thisElement == null) {
                if (thatElement != null) {
                    return false;
                }
            } else if (!thisElement.equals(thatElement)) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean stackOverflow() {
        return false;  // MyArrayList dynamically resizes, so stack never overflows
    }
}