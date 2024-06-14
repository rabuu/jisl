package de.rbuurman.jisl.parsing;

import java.util.ArrayDeque;
import java.util.Deque;

import de.rbuurman.jisl.lexing.token.SimpleToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.Type;

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

	public boolean finished() {
		return this.size() == 0 || this.tokens.peek().exit();
	}

	public boolean endOfExpression() {
		return this.finished() || this.tokens.peek().isType(Type.CLOSE);
	}

	public Token expect(Token expected) throws ParsingException {
		final var token = this.poll();
		if (token == null)
			throw ParsingException.EmptyTokenQueueException;

		if (!token.equals(expected)) {
			throw new ParsingException("Expected " + expected + " but got " + token);
		}

		return token;
	}

	public Token expect(SimpleToken.Type expected) throws ParsingException {
		return this.expect(new SimpleToken(expected));
	}

	public Object[] toArray() {
		return this.tokens.toArray();
	}

	@Override
	public String toString() {
		return this.tokens.toString();
	}
}
