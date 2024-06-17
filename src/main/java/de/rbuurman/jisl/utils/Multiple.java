package de.rbuurman.jisl.utils;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;

/**
 * Multiple
 */
public class Multiple<T> implements Iterable<T> {
	private ArrayDeque<T> elements = new ArrayDeque<>();

	public void add(T element) {
		this.elements.addLast(element);
	}

	public T poll() {
		return this.elements.pollFirst();
	}

	public int size() {
		return this.elements.size();
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
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
}
