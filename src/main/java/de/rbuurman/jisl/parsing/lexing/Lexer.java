package de.rbuurman.jisl.parsing.lexing;

import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.matcher.AlphabeticMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.CharMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.InverseMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.LineMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.NumericMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.WhitespaceMatcher;
import de.rbuurman.jisl.parsing.lexing.token.CommentToken;
import de.rbuurman.jisl.parsing.lexing.token.FloatToken;
import de.rbuurman.jisl.parsing.lexing.token.IdentToken;
import de.rbuurman.jisl.parsing.lexing.token.IntegerToken;
import de.rbuurman.jisl.parsing.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.parsing.lexing.token.PrimitiveTokenType;
import de.rbuurman.jisl.parsing.lexing.token.StringToken;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public class Lexer extends Cursor {
	public Lexer(String text) {
		super(text);
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

	public Token advance() {
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
			return new IdentToken(name);
		}

		return new PrimitiveToken(PrimitiveTokenType.EOF);
	}
}
