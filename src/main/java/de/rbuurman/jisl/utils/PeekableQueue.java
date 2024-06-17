package de.rbuurman.jisl.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * PeekableQueue
 */
public class PeekableQueue<T> implements Iterable<T> {
	protected Deque<T> elements = new ArrayDeque<T>();

	public void queue(T element) {
		this.elements.addLast(element);
	}

	public T poll() {
		return this.elements.pollFirst();
	}

	public void remove() {
		this.elements.removeFirst();
	}

	public T peek() {
		return this.elements.peekFirst();
	}

	public T peekSecond() {
		final var temp = this.elements.pollFirst();
		T second = this.elements.peek();
		this.elements.addFirst(temp);

		return second;
	}

	public int size() {
		return this.elements.size();
	}

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
