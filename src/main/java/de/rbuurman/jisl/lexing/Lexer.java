package de.rbuurman.jisl.lexing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.element.primitive.*;
import de.rbuurman.jisl.lexing.matcher.*;
import de.rbuurman.jisl.lexing.token.*;

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
			tokens.add(token);

			if (token.exit())
				break;
		}

		return tokens;
	}

	private Token advance() throws LexingException {
		this.eat(new WhitespaceMatcher());

		if (this.isEOF()) {
			return new SimpleToken(SimpleToken.Type.EOF).withSourcePosition(this.position);
		}

		final char firstCharacter = this.peek();
		final SourcePosition firstPosition = this.position;
		switch (firstCharacter) {
			case ';':
				this.eat(new CharMatcher(';'));
				this.eat(new WhitespaceMatcher());
				final String comment = this.eat(new LineMatcher());
				return new CommentToken(comment).withSourcePosition(firstPosition);

			case '"':
				this.bump();
				final String string = this.eat(new InverseMatcher(new CharMatcher('"')));
				final char closingDel = this.bump();
				if (closingDel != '"') {
					throw new LexingException("Unterminated string literal", this.position);
				}
				return new StringPrimitive(string).toToken().withSourcePosition(firstPosition);

			case '#':
				this.bump();
				final String boolStr = this.eat(new WordMatcher());
				switch (boolStr) {
					case "true":
						return new BooleanPrimitive(true).toToken().withSourcePosition(firstPosition);
					case "false":
						return new BooleanPrimitive(false).toToken().withSourcePosition(firstPosition);
					default:
						throw new LexingException("Invalid Boolean: #" + boolStr, firstPosition);
				}

			case '(':
			case '[':
				this.bump();
				return new SimpleToken(SimpleToken.Type.OPEN).withSourcePosition(firstPosition);
			case ')':
			case ']':
				this.bump();
				return new SimpleToken(SimpleToken.Type.CLOSE).withSourcePosition(firstPosition);
			case '+':
				this.bump();
				return new SimpleToken(SimpleToken.Type.PLUS).withSourcePosition(firstPosition);
			case '-':
				this.bump();
				return new SimpleToken(SimpleToken.Type.MINUS).withSourcePosition(firstPosition);
		}

		if (Character.isDigit(firstCharacter)) {
			final String numeric = this.eat(new NumericMatcher());
			if (numeric.contains(".")) {
				try {
					final float f = Float.parseFloat(numeric);
					return new FloatPrimitive(f).toToken().withSourcePosition(firstPosition);
				} catch (NumberFormatException e) {
					throw new LexingException("Couldn't parse float", firstPosition);
				}
			} else {
				try {
					final int i = Integer.parseInt(numeric);
					return new IntegerPrimitive(i).toToken().withSourcePosition(firstPosition);
				} catch (Exception e) {
					throw new LexingException("Couldn't parse integer", firstPosition);
				}
			}
		} else if (Character.isAlphabetic(firstCharacter)) {
			final String name = this.eat(new WordMatcher());
			switch (name) {
				case "define":
					return new SimpleToken(SimpleToken.Type.DEFINE).withSourcePosition(firstPosition);
				case "lambda":
					return new SimpleToken(SimpleToken.Type.LAMBDA).withSourcePosition(firstPosition);
				default:
					return new IdentToken(name).withSourcePosition(firstPosition);
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
