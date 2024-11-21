package implementations;

import implementations.Iterator;
import java.util.NoSuchElementException;

import ADT.ListADT;

public class MyArrayList<E> implements ListADT<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private E[] elements;
	private int size;

	@SuppressWarnings("unchecked")
	public MyArrayList() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null element");
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		ensureCapacity();

		// Shift elements right to make space
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}

		elements[index] = toAdd;
		size++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException {
		return add(size, toAdd);
	}

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

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return elements[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		E removed = elements[index];

		// Shift elements left to fill gap
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;
		return removed;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("Cannot remove null element");
		}

		for (int i = 0; i < size; i++) {
			if (toRemove.equals(elements[i])) {
				return remove(i);
			}
		}
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException("Cannot set null element");
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		E oldElement = elements[index];
		elements[index] = toChange;
		return oldElement;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot search for null element");
		}

		for (int i = 0; i < size; i++) {
			if (toFind.equals(elements[i])) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Array cannot be null");
		}

		if (toHold.length < size) {
			toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
		}

		System.arraycopy(elements, 0, toHold, 0, size);

		if (toHold.length > size) {
			toHold[size] = null;
		}

		return toHold;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		System.arraycopy(elements, 0, result, 0, size);
		return result;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}

	private void ensureCapacity() {
		if (size == elements.length) {
			@SuppressWarnings("unchecked")
			E[] newElements = (E[]) new Object[elements.length * 2];
			System.arraycopy(elements, 0, newElements, 0, size);
			elements = newElements;
		}
	}

	private class ArrayListIterator implements Iterator<E> {
		private int current = 0;
		private final E[] iteratorElements;
		private final int iteratorSize;

		@SuppressWarnings("unchecked")
		public ArrayListIterator() {
			iteratorElements = (E[]) new Object[size];
			System.arraycopy(elements, 0, iteratorElements, 0, size);
			iteratorSize = size;
		}

		@Override
		public boolean hasNext() {
			return current < iteratorSize;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return iteratorElements[current++];
		}
	}
}