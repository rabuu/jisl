package de.rbuurman.jisl.lexing;

import de.rbuurman.jisl.utils.PeekableQueue;
import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.program.value.primitive.*;
import de.rbuurman.jisl.lexing.matcher.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

public final class Lexer extends PeekableQueue<Character> {
	final static char EOF = '\0';

	private SourcePosition position;

	public Lexer(final String text) {
		for (char c : text.toCharArray()) {
			this.elements.add(c);
		}

		this.position = new SourcePosition(1, 1);
	}

	/**
	 * This is the main entry point to the Lexer.
	 * <p>
	 * The tokenize() method goes through the literal characters of the
	 * given source text and splits them up into meaningful pieces (Tokens).
	 * <p>
	 * Internally this is done by iteratively calling the advance() method,
	 * which returns the next Token, and collecting them into a TokenQueue.
	 *
	 * @return the code neatly split up into Tokens, ready to be parsed
	 */
	public TokenQueue tokenize() throws LexingException {
		var tokens = new TokenQueue();

		for (var token = this.advance();; token = this.advance()) {
			tokens.queue(token);

			if (token.exit())
				break;
		}

		return tokens;
	}

	/**
	 * Here is where the hard work is done.
	 * This method looks at the upcoming character and decides what Token fits.
	 * The logic is split up into several helper methods called advanceXY(),
	 * each of them analyzing one specific type of Token.
	 *
	 * @return next Token of the source text
	 */
	private Token<?> advance() throws LexingException {
		this.eat(new WhitespaceMatcher());

		if (this.isEOF()) {
			return new SimpleToken(SimpleTokenType.EOF, this.position);
		}

		final char firstCharacter = this.peek();
		final SourcePosition firstPosition = this.position;
		switch (firstCharacter) {
			case ';':
				this.eat(new LineMatcher());
				return this.advance();
			case '#':
				this.bump();
				if (this.peek() == '\\') {
					return advanceCharacter(firstPosition);
				} else {
					return advanceBoolean(firstPosition);
				}
			case '"':
				return advanceString(firstPosition);
			case '(', '[':
				this.bump();
				return new SimpleToken(SimpleTokenType.OPEN, firstPosition);
			case ')', ']':
				this.bump();
				return new SimpleToken(SimpleTokenType.CLOSE, firstPosition);
			case '+':
				this.bump();
				return new SimpleToken(SimpleTokenType.PLUS, firstPosition);
			case '-':
				final char following = this.peekSecond();
				if (Character.isDigit(following)) {
					break;
				}
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
				return advanceNumber(word, firstPosition);
			} catch (NumberFormatException ignored) {
			}
		}

		return switch (word) {
			case "require" -> new SimpleToken(SimpleTokenType.REQUIRE, firstPosition);
			case "true" -> new BooleanPrimitive(true).toToken(firstPosition);
			case "false" -> new BooleanPrimitive(false).toToken(firstPosition);
			case "define" -> new SimpleToken(SimpleTokenType.DEFINE, firstPosition);
			case "lambda" -> new SimpleToken(SimpleTokenType.LAMBDA, firstPosition);
			case "local" -> new SimpleToken(SimpleTokenType.LOCAL, firstPosition);
			case "cond" -> new SimpleToken(SimpleTokenType.COND, firstPosition);
			case "else" -> new SimpleToken(SimpleTokenType.ELSE, firstPosition);
			case "if" -> new SimpleToken(SimpleTokenType.IF, firstPosition);
			case "and" -> new SimpleToken(SimpleTokenType.AND, firstPosition);
			case "or" -> new SimpleToken(SimpleTokenType.OR, firstPosition);
			case "not" -> new SimpleToken(SimpleTokenType.NOT, firstPosition);
			case "identity" -> new SimpleToken(SimpleTokenType.IDENTITY, firstPosition);
			default -> new VariableNameToken(word, firstPosition);
		};
	}

	/**
	 * Tokenize a string
	 */
	private Token<?> advanceString(SourcePosition firstPosition) throws LexingException {
		this.bump();
		StringBuilder string = new StringBuilder();
		for (;;) {
			final String s = this
					.eat(new InverseMatcher(
							new OrMatcher(new CharMatcher('\\'), new CharMatcher('"'))));

			final char delimiter = this.bump();

			// escape codes
			if (delimiter == '\\') {
				final var escapeCodePosition = this.position;
				final var escapeCode = this.bump();
				if (escapeCode == '"') {
					string.append(s).append('"');
					continue;
				}
				throw new LexingException("Invalid escape code " + escapeCode, escapeCodePosition);
			} else if (delimiter == '"') {
				string.append(s);
				break;
			} else {
				throw new LexingException("Unterminated string literal", this.position);
			}
		}
		return new StringPrimitive(string.toString()).toToken(firstPosition);
	}

	/**
	 * Tokenize a character
	 */
	private Token<?> advanceCharacter(SourcePosition firstPosition) throws LexingException {
		this.bump();
		final String charString = this.eat(new WordMatcher());

		if (charString.length() == 1) {
			return new CharacterPrimitive(charString.charAt(0)).toToken(firstPosition);
		}

		if (charString.equals("space")) {
			return new CharacterPrimitive(' ').toToken(firstPosition);
		}
		throw new LexingException("Invalid character literal " + charString, firstPosition);
	}

	/**
	 * Tokenize a boolean
	 * <p>
	 * There is a special syntax #reader that looks like a boolean,
	 * we simply ignore the line that follows a #reader.
	 */
	private Token<?> advanceBoolean(SourcePosition firstPosition) throws LexingException {
		final String boolStr = this.eat(new WordMatcher());
		return switch (boolStr) {
			case "true", "t", "T" -> new BooleanPrimitive(true).toToken(firstPosition);
			case "false", "f", "F" -> new BooleanPrimitive(false).toToken(firstPosition);
			case "reader" -> {
				this.eat(new LineMatcher());
				yield this.advance();
			}
			default -> throw new LexingException("Invalid boolean #" + boolStr, firstPosition);
		};
	}

	/**
	 * Tokenize a number
	 * 
	 * @param word that possibly is a number
	 * @throws NumberFormatException when the number can't be parsed
	 */
	private Token<?> advanceNumber(String word, SourcePosition firstPosition)
			throws LexingException, NumberFormatException {
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
	}

	@Override
	public Character peek() {
		final Character c = super.peek();

		if (c == null)
			return EOF;
		else
			return c;
	}

	private char bump() {
		final Character c = this.elements.poll();
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
		return this.elements.isEmpty();
	}
}
