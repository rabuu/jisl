package de.rbuurman.jisl.parsing.lexing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.matcher.*;
import de.rbuurman.jisl.parsing.lexing.token.*;

public final class Lexer {
	final static char EOF = '\0';

	private Queue<Character> chars;
	private SourcePosition position;

	public Lexer(final String text) {
		final var chars = new LinkedList<Character>();

		for (char c : text.toCharArray()) {
			chars.add(c);
		}

		this.chars = chars;
		this.position = new SourcePosition(1, 1);
	}

	public Queue<Token> tokenize() throws LexingException {
		var tokens = new LinkedList<Token>();

		while (true) {
			var token = this.advance();
			final boolean exit = token.exit();
			tokens.add(token);

			if (exit)
				break;
		}

		return tokens;
	}

	private Token advance() throws LexingException {
		this.eat(new WhitespaceMatcher());

		if (this.isEOF()) {
			return new SimpleToken(SimpleTokenType.EOF, this.position);
		}

		final char firstCharacter = this.peek();
		final SourcePosition firstPosition = this.position;
		switch (firstCharacter) {
			case ';':
				this.eat(new CharMatcher(';'));
				this.eat(new WhitespaceMatcher());
				final String comment = this.eat(new LineMatcher());
				return new CommentToken(comment, firstPosition);

			case '"':
				this.bump();
				final String string = this.eat(new InverseMatcher(new CharMatcher('"')));
				final char closingDel = this.bump();
				if (closingDel != '"') {
					throw new LexingException("Unterminated string literal", this.position);
				}
				return new StringToken(string, firstPosition);

			case '#':
				this.bump();
				final String boolStr = this.eat(new WordMatcher());
				switch (boolStr) {
					case "true":
						return new BooleanToken(true, firstPosition);
					case "false":
						return new BooleanToken(false, firstPosition);
					default:
						throw new LexingException("Invalid Boolean: #" + boolStr, firstPosition);
				}

			case '(':
			case '[':
				this.bump();
				return new SimpleToken(SimpleTokenType.OPEN, firstPosition);
			case ')':
			case ']':
				this.bump();
				return new SimpleToken(SimpleTokenType.CLOSE, firstPosition);
			case '+':
				this.bump();
				return new SimpleToken(SimpleTokenType.PLUS, firstPosition);
			case '-':
				this.bump();
				return new SimpleToken(SimpleTokenType.MINUS, firstPosition);
		}

		if (Character.isDigit(firstCharacter)) {
			final String numeric = this.eat(new NumericMatcher());
			if (numeric.contains(".")) {
				try {
					final float f = Float.parseFloat(numeric);
					return new FloatToken(f, firstPosition);
				} catch (NumberFormatException e) {
					throw new LexingException("Couldn't parse float", firstPosition);
				}
			} else {
				try {
					final int i = Integer.parseInt(numeric);
					return new IntegerToken(i, firstPosition);
				} catch (Exception e) {
					throw new LexingException("Couldn't parse integer", firstPosition);
				}
			}
		} else if (Character.isAlphabetic(firstCharacter)) {
			final String name = this.eat(new WordMatcher());
			switch (name) {
				case "define":
					return new SimpleToken(SimpleTokenType.DEFINE, firstPosition);
				default:
					return new IdentToken(name, firstPosition);
			}
		}

		throw new LexingException("Illegal character: " + firstCharacter, firstPosition);
	}

	private char peek() {
		final Character c = this.chars.peek();

		if (c == null)
			return EOF;
		else
			return c;
	}

	private char bump() {
		final Character c = this.chars.poll();
		if (c == null)
			return EOF;

		if (c == '\n') {
			this.position = new SourcePosition(this.position.row() + 1, 1);
		} else {
			this.position = new SourcePosition(this.position.row(), this.position.column() + 1);
		}

		return c;
	}

	private String eat(Matcher matcher) {
		var builder = new StringBuilder();
		while (!this.isEOF() && matcher.matches(this.peek())) {
			builder.append(this.bump());
		}

		return builder.toString();
	}

	private boolean isEOF() {
		return this.chars.isEmpty();
	}
}
