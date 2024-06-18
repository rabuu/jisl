package de.rbuurman.jisl.lexing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.program.value.primitive.*;
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

		for (var token = this.advance();; token = this.advance()) {
			tokens.queue(token);

			if (token.exit())
				break;
		}

		return tokens;
	}

	private Token<?> advance() throws LexingException {
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
				var string = new String();
				for (;;) {
					final String s = this
							.eat(new InverseMatcher(new OrMatcher(new CharMatcher('\\'), new CharMatcher('"'))));

					final char delimiter = this.bump();

					// escape codes
					if (delimiter == '\\') {
						final var escapeCodePosition = this.position;
						final var escapeCode = this.bump();
						switch (escapeCode) {
							case '"':
								string += s + '"';
								continue;

							default:
								throw new LexingException("Invalid escape code " + escapeCode, escapeCodePosition);
						}
					} else if (delimiter == '"') {
						string += s;
						break;
					} else {
						throw new LexingException("Unterminated string literal", this.position);
					}
				}
				return new StringPrimitive(string).toToken(firstPosition);

			case '#':
				this.bump();

				// character
				if (this.peek() == '\\') {
					this.bump();
					final String charString = this.eat(new WordMatcher());

					if (charString.length() == 1) {
						return new CharacterPrimitive(charString.charAt(0)).toToken(firstPosition);
					}

					switch (charString) {
						case "space":
							return new CharacterPrimitive(' ').toToken(firstPosition);
						default:
							throw new LexingException("Invalid character literal " + charString, firstPosition);
					}
				}

				// booleans & specials
				final String boolStr = this.eat(new WordMatcher());
				switch (boolStr) {
					case "true":
					case "t":
					case "T":
						return new BooleanPrimitive(true).toToken(firstPosition);
					case "false":
					case "f":
					case "F":
						return new BooleanPrimitive(false).toToken(firstPosition);
					case "reader":
						// ignore
						this.eat(new LineMatcher());
						return this.advance();
					default:
						throw new LexingException("Invalid boolean #" + boolStr, firstPosition);
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
			case '*':
				this.bump();
				return new SimpleToken(SimpleTokenType.ASTERISK, firstPosition);
			case '/':
				this.bump();
				return new SimpleToken(SimpleTokenType.SLASH, firstPosition);
		}

		final String word = this.eat(new WordMatcher());
		if (new NumericMatcher().matches(word)) {
			try {
				if (word.contains("/")) {
					String[] fraction = word.split("/");
					if (fraction.length != 2) {
						throw new LexingException("Invalid fraction literal: " + word, firstPosition);
					}
					final var dividend = Double.parseDouble(fraction[0]);
					final var divisor = Double.parseDouble(fraction[1]);
					return new NumberPrimitive(dividend / divisor).toToken(firstPosition);
				}
				var num = Double.parseDouble(word);
				return new NumberPrimitive(num).toToken(firstPosition);
			} catch (NumberFormatException e) {
			}
		}

		switch (word) {
			case "require":
				return new SimpleToken(SimpleTokenType.REQUIRE, firstPosition);
			case "true":
				return new BooleanPrimitive(true).toToken(firstPosition);
			case "false":
				return new BooleanPrimitive(false).toToken(firstPosition);
			case "define":
				return new SimpleToken(SimpleTokenType.DEFINE, firstPosition);
			case "lambda":
				return new SimpleToken(SimpleTokenType.LAMBDA, firstPosition);
			case "local":
				return new SimpleToken(SimpleTokenType.LOCAL, firstPosition);
			case "cond":
				return new SimpleToken(SimpleTokenType.COND, firstPosition);
			case "else":
				return new SimpleToken(SimpleTokenType.ELSE, firstPosition);
			case "if":
				return new SimpleToken(SimpleTokenType.IF, firstPosition);
			case "and":
				return new SimpleToken(SimpleTokenType.AND, firstPosition);
			case "or":
				return new SimpleToken(SimpleTokenType.OR, firstPosition);
			case "not":
				return new SimpleToken(SimpleTokenType.NOT, firstPosition);
			case "identity":
				return new SimpleToken(SimpleTokenType.IDENTITY, firstPosition);
			default:
				return new VariableNameToken(word, firstPosition);
		}
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
