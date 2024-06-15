package de.rbuurman.jisl.lexing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.program.primitive.*;
import de.rbuurman.jisl.lexing.matcher.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

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

	public TokenQueue tokenize() throws LexingException {
		var tokens = new TokenQueue();

		while (true) {
			var token = this.advance();
			tokens.queue(token);

			if (token.exit())
				break;
		}

		return tokens;
	}

	private Token advance() throws LexingException {
		this.eat(new WhitespaceMatcher());

		if (this.isEOF()) {
			return new SimpleToken(SimpleTokenType.EOF).withPosition(this.position);
		}

		final char firstCharacter = this.peek();
		final SourcePosition firstPosition = this.position;
		switch (firstCharacter) {
			case ';':
				this.eat(new CharMatcher(';'));
				this.eat(new WhitespaceMatcher());
				final String comment = this.eat(new LineMatcher());
				return new CommentToken(comment).withPosition(firstPosition);

			case '"':
				this.bump();
				final String string = this.eat(new InverseMatcher(new CharMatcher('"')));
				final char closingDel = this.bump();
				if (closingDel != '"') {
					throw new LexingException("Unterminated string literal", this.position);
				}
				return new StringPrimitive(string).toToken().withPosition(firstPosition);

			case '#':
				this.bump();
				final String boolStr = this.eat(new WordMatcher());
				switch (boolStr) {
					case "true":
						return new BooleanPrimitive(true).toToken().withPosition(firstPosition);
					case "false":
						return new BooleanPrimitive(false).toToken().withPosition(firstPosition);
					default:
						throw new LexingException("Invalid Boolean: #" + boolStr, firstPosition);
				}

			case '(':
			case '[':
				this.bump();
				return new SimpleToken(SimpleTokenType.OPEN).withPosition(firstPosition);
			case ')':
			case ']':
				this.bump();
				return new SimpleToken(SimpleTokenType.CLOSE).withPosition(firstPosition);
			case '+':
				this.bump();
				return new SimpleToken(SimpleTokenType.PLUS).withPosition(firstPosition);
			case '-':
				this.bump();
				return new SimpleToken(SimpleTokenType.MINUS).withPosition(firstPosition);
		}

		if (Character.isDigit(firstCharacter)) {
			final String numeric = this.eat(new NumericMatcher());
			if (numeric.contains(".")) {
				try {
					final float f = Float.parseFloat(numeric);
					return new FloatPrimitive(f).toToken().withPosition(firstPosition);
				} catch (NumberFormatException e) {
					throw new LexingException("Couldn't parse float", firstPosition);
				}
			} else {
				try {
					final int i = Integer.parseInt(numeric);
					return new IntegerPrimitive(i).toToken().withPosition(firstPosition);
				} catch (Exception e) {
					throw new LexingException("Couldn't parse integer", firstPosition);
				}
			}
		} else if (Character.isAlphabetic(firstCharacter)) {
			final String name = this.eat(new WordMatcher());
			switch (name) {
				case "define":
					return new SimpleToken(SimpleTokenType.DEFINE).withPosition(firstPosition);
				case "lambda":
					return new SimpleToken(SimpleTokenType.LAMBDA).withPosition(firstPosition);
				default:
					return new IdentifierToken(name).withPosition(firstPosition);
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
			this.position = this.position.nextRow();
		} else {
			this.position = this.position.nextColumn();
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
