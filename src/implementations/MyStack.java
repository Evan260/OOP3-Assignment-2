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
        return elements.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        return elements.toArray(holder);
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
        private int index = 0;
        private final E[] iteratorElements;
        
        @SuppressWarnings("unchecked")
        public StackIterator() {
            // Create a copy of elements as required by Iterator interface
            iteratorElements = (E[]) new Object[size()];
            for (int i = 0; i < size(); i++) {
                iteratorElements[i] = elements.get(i);
            }
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
            return iteratorElements[index++];
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