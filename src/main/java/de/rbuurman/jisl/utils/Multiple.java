package de.rbuurman.jisl.utils;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Multiple is a custom collection type for JISL.
 * It is just a Queue with some custom implementations.
 */
public class Multiple<T> implements Iterable<T> {
	private ArrayDeque<T> elements = new ArrayDeque<>();

	/**
	 * Copy a Multiple
	 * 
	 * @param other the Multiple that should be copied
	 * @return the copy
	 */
	public static <T> Multiple<T> copy(Multiple<T> other) {
		var id = new Multiple<T>();
		id.elements = other.elements.clone();
		return id;
	}

	/**
	 * Add an element to the Multiple queue
	 */
	public void add(T element) {
		this.elements.addLast(element);
	}

	/**
	 * Add an element to the front of the Multiple queue
	 */
	public void addFront(T element) {
		this.elements.addFirst(element);
	}

	/**
	 * Poll the next element
	 */
	public T poll() {
		return this.elements.pollFirst();
	}

	/**
	 * Returns how many elements are in the Multiple queue
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * Returns whether the Multiple queue is empty
	 */
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	/**
	 * Returns the index of a given element
	 *
	 * @return the index if the element was found, -1 otherwise
	 */
	public int find(final T element) {
		var copy = Multiple.copy(this);
		int i = 0;
		for (var other : copy) {
			if (other.equals(element)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * Returns whether a given element is in the Multiple queue
	 *
	 * @return true if it contains the element, false otherwise
	 */
	public boolean contains(final T element) {
		return this.elements.contains(element);
	}

	/**
	 * Get the Multiple in reversed order (without modifying it)
	 *
	 * @return the reversed Multiple
	 */
	public Multiple<T> reversed() {
		Multiple<T> reversed = new Multiple<>();
		for (var elem : this) {
			reversed.elements.addFirst(elem);
		}
		return reversed;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		@SuppressWarnings("unchecked")
		final Multiple<T> other = (Multiple<T>) obj;

		if (other.elements.size() != this.elements.size())
			return false;

		final Iterator<T> thisElements = this.elements.iterator();
		final Iterator<T> otherElements = other.elements.iterator();

		while (thisElements.hasNext()) {
			if (!Objects.equals(thisElements.next(), otherElements.next()))
				return false;
		}

		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return this.elements.iterator();
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}
}
