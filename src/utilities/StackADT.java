package utilities;

import java.io.Serializable;

/**
 * <p>
 * The <code>StackADT</code> interface represents a last-in-first-out (LIFO) stack of objects.
 * This interface defines all the basic operations that a stack should support.
 * Implementing classes must ensure proper stack behavior and handle edge cases appropriately.
 * </p>
 * 
 * @param <E> The type of elements this stack holds
 */
public interface StackADT<E> extends Serializable
{
    /**
     * Pushes an element onto the top of the stack.
     * 
     * @param toAdd The element to be pushed onto the stack
     * @return true if the element was successfully added
     * @throws NullPointerException if the specified element is null and the
     *         stack implementation does not support null elements
     */
    public boolean push(E toAdd) throws NullPointerException;
    
    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return The element removed from the top of the stack
     * @throws java.util.EmptyStackException if the stack is empty
     */
    public E pop();
    
    /**
     * Looks at the element at the top of the stack without removing it.
     * 
     * @return The element at the top of the stack
     * @throws java.util.EmptyStackException if the stack is empty
     */
    public E peek();
    
    /**
     * Returns the number of elements in the stack.
     * 
     * @return The current size of the stack
     */
    public int size();
    
    /**
     * Tests if this stack is empty.
     * 
     * @return true if the stack contains no elements
     */
    public boolean isEmpty();
    
    /**
     * Returns true if the specified element is in this stack.
     * 
     * @param toFind The element whose presence in the stack is to be tested
     * @return true if the element is found in the stack
     * @throws NullPointerException if the specified element is null and the
     *         stack implementation does not support null elements
     */
    public boolean contains(E toFind) throws NullPointerException;
    
    /**
     * Removes all elements from the stack.
     * The stack will be empty after this call returns.
     */
    public void clear();
    
    /**
     * Returns an array containing all of the elements in this stack in proper sequence
     * (from bottom to top).
     * 
     * @return An array containing all of the elements in this stack
     */
    public Object[] toArray();
    
    /**
     * Returns an array containing all of the elements in this stack in proper sequence
     * (from bottom to top). The runtime type of the returned array is that of the
     * specified array.
     * 
     * @param holder The array into which the elements of the stack are to be stored;
     *              if it is not large enough, a new array of the same runtime type is allocated
     * @return An array containing the elements of the stack
     * @throws NullPointerException if the specified array is null
     */
    public E[] toArray(E[] holder) throws NullPointerException;
    
    /**
     * Returns an iterator over the elements in this stack in proper sequence.
     * The elements are returned from bottom to top.
     * 
     * @return An iterator over the elements in this stack in proper sequence
     */
    public Iterator<E> iterator();
}