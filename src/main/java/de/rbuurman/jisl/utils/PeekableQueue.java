package de.rbuurman.jisl.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * PeekableQueue is a custom queue with peeking capacities for JISL.
 * It is used throughout the project for types that need peeking.
 */
public class PeekableQueue<T> implements Iterable<T> {
	protected Deque<T> elements = new ArrayDeque<T>();

	/**
	 * Add an element to the tail of the queue
	 */
	public void queue(T element) {
		this.elements.addLast(element);
	}

	/**
	 * Poll (get & remove) the first element of the queue
	 */
	public T poll() {
		return this.elements.pollFirst();
	}

	/**
	 * Remove the first element of the queue
	 */
	public void remove() {
		this.elements.removeFirst();
	}

	/**
	 * Get the first element of the queue without removing it
	 */
	public T peek() {
		return this.elements.peekFirst();
	}

	/**
	 * Get the second element of the queue without modifying the queue
	 */
	public T peekSecond() {
		final var temp = this.elements.pollFirst();
		T second = this.elements.peek();
		this.elements.addFirst(temp);

		return second;
	}

	/**
	 * Get the third element of the queue without modifying the queue
	 */
	public T peekThird() {
		final var temp1 = this.elements.pollFirst();
		final var temp2 = this.elements.pollFirst();
		T third = this.elements.peek();
		this.elements.addFirst(temp2);
		this.elements.addFirst(temp1);

		return third;
	}

	/**
	 * Returns how many element are in the queue
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * Returns whether the queue is empty
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Turn the queue into an array
	 */
	public Object[] toArray() {
		return this.elements.toArray();
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return this.elements.iterator();
	}
}
