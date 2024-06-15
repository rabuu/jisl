package de.rbuurman.jisl.utils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * PeekableQueue
 */
public class PeekableQueue<T> {
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

	public T peekNth(int n) {
		if (n <= 0)
			return null;

		for (int i = 0; i < n - 1; i++) {
			this.elements.addLast(this.elements.pollFirst());
		}

		T nth = this.elements.peek();

		for (int i = 0; i < n - 1; ++i) {
			this.elements.addFirst(this.elements.pollLast());
		}

		return nth;
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
}
