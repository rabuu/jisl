package de.rbuurman.jisl.parsing;

import java.util.ArrayDeque;
import java.util.Deque;

import de.rbuurman.jisl.lexing.token.Token;

public class TokenQueue {
	private Deque<Token> tokens = new ArrayDeque<Token>();

	public void queue(Token token) {
		this.tokens.addLast(token);
	}

	public Token poll() {
		return this.tokens.pollFirst();
	}

	public void remove() {
		this.tokens.removeFirst();
	}

	public Token peek() {
		return this.tokens.peekFirst();
	}

	public Token peekNth(int n) {
		if (n <= 0)
			return null;

		for (int i = 0; i < n - 1; i++) {
			this.tokens.addLast(this.tokens.pollFirst());
		}

		Token nth = this.tokens.peek();

		for (int i = 0; i < n - 1; ++i) {
			this.tokens.addFirst(this.tokens.pollLast());
		}

		return nth;
	}

	public int size() {
		return this.tokens.size();
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public void expectToken(Token expected) throws ParsingException {
		final var token = this.poll();
		if (token == null)
			throw ParsingException.EmptyTokenQueueException;

		if (!token.equals(expected)) {
			throw new ParsingException("Expected " + expected + " but got " + token);
		}
	}

	public Object[] toArray() {
		return this.tokens.toArray();
	}
}
