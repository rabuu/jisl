package de.rbuurman.jisl.parsing.lexing;

import java.util.EmptyStackException;
import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.matcher.*;
import de.rbuurman.jisl.parsing.lexing.token.*;

public class Lexer {
	final static char EOF = '\0';

	private Stack<Character> chars;

	public Lexer(final String text) {
		final var chars = new Stack<Character>();

		var revText = new StringBuilder(text);
		revText.reverse();

		for (char c : revText.toString().toCharArray()) {
			chars.push(c);
		}

		this.chars = chars;
	}

	public static Stack<Token> tokenize(String text) {
		final var cursor = new Lexer(text);
		var tokens = new Stack<Token>();

		while (true) {
			var token = cursor.advance();
			final boolean exit = token.exit();
			tokens.push(token);

			if (exit)
				break;
		}

		return tokens;
	}

	private Token advance() {
		this.eat(new WhitespaceMatcher());

		final char firstCharacter = this.peek();
		switch (firstCharacter) {
			case ';':
				this.eat(new CharMatcher(';'));
				this.eat(new WhitespaceMatcher());
				final String comment = this.eat(new LineMatcher());
				return new CommentToken(comment);

			case '"':
				this.bump();
				final String string = this.eat(new InverseMatcher(new CharMatcher('"')));
				final char closingDel = this.bump();
				assert closingDel == '"';
				return new StringToken(string);

			case '(':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.PAREN_OPEN);
			case ')':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.PAREN_CLOSE);
			case '[':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.BRACKET_OPEN);
			case ']':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.BRACKET_CLOSE);
			case '+':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.PLUS);
			case '-':
				this.bump();
				return new PrimitiveToken(PrimitiveTokenType.MINUS);

			default:
				break;
		}

		if (Character.isDigit(firstCharacter)) {
			final String numeric = this.eat(new NumericMatcher());
			if (numeric.contains(".")) {
				final float f = Float.parseFloat(numeric);
				return new FloatToken(f);
			} else {
				final int i = Integer.parseInt(numeric);
				return new IntegerToken(i);
			}
		} else if (Character.isAlphabetic(firstCharacter)) {
			final String name = this.eat(new AlphabeticMatcher());
			switch (name) {
				case "define":
					return new KeywordToken(Keyword.DEFINE);
				default:
					return new IdentToken(name);
			}
		}

		return new PrimitiveToken(PrimitiveTokenType.EOF);
	}

	private char peek() {
		try {
			return this.chars.peek();
		} catch (EmptyStackException e) {
			return EOF;
		}
	}

	private char bump() {
		try {
			final char c = this.chars.pop();
			return c;
		} catch (EmptyStackException e) {
			return EOF;
		}
	}

	private String eat(Matcher matcher) {
		var builder = new StringBuilder();
		while (!this.isEOF() && matcher.matches(this.peek())) {
			builder.append(this.bump());
		}

		return builder.toString();
	}

	private boolean isEOF() {
		return this.chars.empty();
	}
}
