package de.rbuurman.jisl.parsing.lexing;

import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.matcher.CharMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.InverseMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.LineMatcher;
import de.rbuurman.jisl.parsing.lexing.matcher.WhitespaceMatcher;
import de.rbuurman.jisl.parsing.lexing.token.CommentToken;
import de.rbuurman.jisl.parsing.lexing.token.PrimitiveToken;
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

		switch (this.peek()) {
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
				return PrimitiveToken.PAREN_OPEN;
			case ')':
				return PrimitiveToken.PAREN_CLOSE;
			case '[':
				return PrimitiveToken.BRACKET_OPEN;
			case ']':
				return PrimitiveToken.BRACKET_CLOSE;
			case '+':
				return PrimitiveToken.PLUS;
			case '-':
				return PrimitiveToken.MINUS;

			default:
				break;
		}

		return PrimitiveToken.EOF;
	}
}
