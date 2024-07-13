package de.rbuurman.jisl.lexing;

import de.rbuurman.jisl.utils.PeekableQueue;
import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.program.value.primitive.*;
import de.rbuurman.jisl.lexing.matcher.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

/**
 * The Lexer class is responsible for turning the source code into
 * a number of Tokens, so that the rest of the code doesn't have to work
 * with strings or characters but with meaningful abstracted data.
 * <p>
 * To use this class, first construct a Lexer with the source text as string.
 * Then, call the tokenize() method on it.
 */
public final class Lexer {
	final static char EOF = '\0';

	private PeekableQueue<Character> chars = new PeekableQueue<>();
	private SourcePosition position;

	/**
	 * Construct a Lexer
	 *
	 * @param text the literal text that should get tokenized
	 */
	public Lexer(final String text) {
		for (char c : text.toCharArray()) {
			this.chars.queue(c);
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

		if (this.chars.isEmpty()) {
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
			case '\'':
				return advanceSymbol(firstPosition);
			case '(', '[':
				this.bump();
				return new SimpleToken(SimpleTokenType.OPEN, firstPosition);
			case ')', ']':
				this.bump();
				return new SimpleToken(SimpleTokenType.CLOSE, firstPosition);
		}

		final String word = this.eat(new WordMatcher());

		if (word.isEmpty()) {
			throw new LexingException("Failed to tokenize " + this.peek(), firstPosition);
		}

		if (new NumericMatcher().matches(word) && !word.equals("/")) {
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
			case "define-struct" -> new SimpleToken(SimpleTokenType.DEFINE_STRUCT, firstPosition);
			case "lambda", "λ" -> new SimpleToken(SimpleTokenType.LAMBDA, firstPosition);
			case "local" -> new SimpleToken(SimpleTokenType.LOCAL, firstPosition);
			case "cond" -> new SimpleToken(SimpleTokenType.COND, firstPosition);
			case "else" -> new SimpleToken(SimpleTokenType.ELSE, firstPosition);
			case "if" -> new SimpleToken(SimpleTokenType.IF, firstPosition);
			case "and" -> new SimpleToken(SimpleTokenType.AND, firstPosition);
			case "or" -> new SimpleToken(SimpleTokenType.OR, firstPosition);
			case "not" -> new SimpleToken(SimpleTokenType.NOT, firstPosition);
			case "empty" -> new SimpleToken(SimpleTokenType.EMPTY, firstPosition);
			case "cons" -> new SimpleToken(SimpleTokenType.CONS, firstPosition);
			case "list" -> new SimpleToken(SimpleTokenType.LIST, firstPosition);
			case "make-list" -> new SimpleToken(SimpleTokenType.MAKE_LIST, firstPosition);
			case "apply" -> new SimpleToken(SimpleTokenType.APPLY, firstPosition);
			case "car" -> new SimpleToken(SimpleTokenType.CAR, firstPosition);
			case "cdr" -> new SimpleToken(SimpleTokenType.CDR, firstPosition);
			case "append" -> new SimpleToken(SimpleTokenType.APPEND, firstPosition);
			case "eq?" -> new SimpleToken(SimpleTokenType.EQUALITY, firstPosition);
			case "+" -> new SimpleToken(SimpleTokenType.PLUS, firstPosition);
			case "-" -> new SimpleToken(SimpleTokenType.MINUS, firstPosition);
			case "*" -> new SimpleToken(SimpleTokenType.ASTERISK, firstPosition);
			case "/" -> new SimpleToken(SimpleTokenType.SLASH, firstPosition);
			case "min" -> new SimpleToken(SimpleTokenType.MIN, firstPosition);
			case "max" -> new SimpleToken(SimpleTokenType.MAX, firstPosition);
			case "exp" -> new SimpleToken(SimpleTokenType.EXP, firstPosition);
			case "expt" -> new SimpleToken(SimpleTokenType.EXPT, firstPosition);
			case "log" -> new SimpleToken(SimpleTokenType.LOG, firstPosition);
			case "sqrt" -> new SimpleToken(SimpleTokenType.SQRT, firstPosition);
			case "ceiling" -> new SimpleToken(SimpleTokenType.CEILING, firstPosition);
			case "floor" -> new SimpleToken(SimpleTokenType.FLOOR, firstPosition);
			case "modulo" -> new SimpleToken(SimpleTokenType.MODULO, firstPosition);
			case "random" -> new SimpleToken(SimpleTokenType.RANDOM, firstPosition);
			case "=" -> new SimpleToken(SimpleTokenType.EQUALS, firstPosition);
			case "<" -> new SimpleToken(SimpleTokenType.LESS, firstPosition);
			case "<=" -> new SimpleToken(SimpleTokenType.LESSEQ, firstPosition);
			case ">" -> new SimpleToken(SimpleTokenType.GREATER, firstPosition);
			case ">=" -> new SimpleToken(SimpleTokenType.GREATEREQ, firstPosition);
			case "false?" -> new SimpleToken(SimpleTokenType.IS_FALSE, firstPosition);
			case "boolean?" -> new SimpleToken(SimpleTokenType.IS_BOOLEAN, firstPosition);
			case "number?" -> new SimpleToken(SimpleTokenType.IS_NUMBER, firstPosition);
			case "integer?" -> new SimpleToken(SimpleTokenType.IS_INTEGER, firstPosition);
			case "symbol?" -> new SimpleToken(SimpleTokenType.IS_SYMBOL, firstPosition);
			case "list?" -> new SimpleToken(SimpleTokenType.IS_LIST, firstPosition);
			case "cons?" -> new SimpleToken(SimpleTokenType.IS_CONS, firstPosition);
			case "empty?" -> new SimpleToken(SimpleTokenType.IS_EMPTY, firstPosition);
			case "char?" -> new SimpleToken(SimpleTokenType.IS_CHARACTER, firstPosition);
			case "string?" -> new SimpleToken(SimpleTokenType.IS_STRING, firstPosition);
			case "struct?" -> new SimpleToken(SimpleTokenType.IS_STRUCT, firstPosition);
			case "procedure?" -> new SimpleToken(SimpleTokenType.IS_PROCEDURE, firstPosition);
			default -> new VariableNameToken(word, firstPosition);
		};
	}

	/**
	 * Tokenize a string
	 *
	 * @return a StringPrimitive
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
	 * Tokenize a symbol
	 *
	 * @return a SymbolPrimitive
	 */
	private Token<?> advanceSymbol(SourcePosition firstPosition) throws LexingException {
		this.bump();
		if (this.peek() == '(' && this.chars.peekSecond() == ')') {
			this.bump();
			this.bump();
			return new SimpleToken(SimpleTokenType.EMPTY, firstPosition);
		}

		final String word = this.eat(new WordMatcher());
		if (word.isEmpty()) {
			throw new LexingException("Empty symbol is not allowed", firstPosition);
		}

		return new SymbolPrimitive(word).toToken(firstPosition);
	}

	/**
	 * Tokenize a character
	 * 
	 * @return a CharacterPrimitive
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
	 * 
	 * @return a BooleanPrimitive
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
	 * @return a NumberPrimitive
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

	/**
	 * Return the current character but don't remove it from the queue
	 */
	private char peek() {
		final Character c = this.chars.peek();

		if (c == null)
			return EOF;
		else
			return c;
	}

	/**
	 * Return the current character and remove it from the queue
	 */
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

	/**
	 * Remove the upcoming characters from the queue as long as they match.
	 * Then, return all of them as a string.
	 *
	 * @param matcher what characters match and when to stop eating
	 */
	private String eat(Matcher matcher) {
		var builder = new StringBuilder();
		while (!this.chars.isEmpty() && matcher.matches(this.peek())) {
			builder.append(this.bump());
		}

		return builder.toString();
	}
}
