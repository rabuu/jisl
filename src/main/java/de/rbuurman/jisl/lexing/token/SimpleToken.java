package de.rbuurman.jisl.lexing.token;

public final class SimpleToken extends Token<SimpleToken.SimpleTokenType> {
	public enum SimpleTokenType {
		OPEN,
		CLOSE,

		REQUIRE,

		PLUS,
		MINUS,
		ASTERISK,
		SLASH,

		COND,
		ELSE,
		IF,
		AND,
		OR,
		NOT,

		IDENTITY,

		DEFINE,
		LAMBDA,

		EOF;
	}

	public SimpleToken(SimpleTokenType type) {
		super(type);
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
