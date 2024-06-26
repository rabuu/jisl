package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * A SimpleToken is a Token that actually doesn't carry any data by itself
 * <p>
 * The different simple Tokens are specified by the SimpleTokenType that
 * enumerates all of them
 */
public final class SimpleToken extends Token<SimpleToken.SimpleTokenType> {
	public enum SimpleTokenType {
		// basic syntax
		OPEN,
		CLOSE,

		// keywords
		REQUIRE,
		DEFINE,
		DEFINE_STRUCT,
		LAMBDA,
		LOCAL,

		// list builtins
		EMPTY,
		CONS,

		// arithmetic builtins
		PLUS,
		MINUS,
		ASTERISK,
		SLASH,

		// logical builtins
		COND,
		ELSE,
		IF,
		AND,
		OR,
		NOT,

		// other builtins
		IDENTITY,
		EQUALITY,

		// end of file
		EOF;
	}

	public SimpleToken(SimpleTokenType type, SourcePosition sourcePosition) {
		super(type, sourcePosition);
	}

	@Override
	public boolean is(SimpleTokenType type) {
		return this.getState() == type;
	}

	@Override
	public boolean exit() {
		return this.getState() == SimpleTokenType.EOF;
	}

	@Override
	public String toString() {
		return this.getState().toString();
	}
}
