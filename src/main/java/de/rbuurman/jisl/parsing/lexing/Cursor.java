package de.rbuurman.jisl.parsing.lexing;

import de.rbuurman.jisl.parsing.lexing.matcher.Matcher;

import java.util.EmptyStackException;
import java.util.Stack;

public class Cursor {
	final static char EOF = '\0';

	private Stack<Character> chars;

	public Cursor(final String text) {
		final var chars = new Stack<Character>();

		var revText = new StringBuilder(text);
		revText.reverse();

		for (char c : revText.toString().toCharArray()) {
			chars.push(c);
		}

		this.chars = chars;
	}

	public char peek() {
		try {
			return this.chars.peek();
		} catch (EmptyStackException e) {
			return EOF;
		}
	}

	public char bump() {
		try {
			final char c = this.chars.pop();
			return c;
		} catch (EmptyStackException e) {
			return EOF;
		}
	}

	public String eat(Matcher matcher) {
		var builder = new StringBuilder();
		while (!this.isEOF() && matcher.matches(this.peek())) {
			builder.append(this.bump());
		}

		return builder.toString();
	}

	public boolean isEOF() {
		return this.chars.empty();
	}
}
