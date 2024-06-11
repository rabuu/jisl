package de.rbuurman.jisl.parsing.lexing.token;

public final class BooleanToken extends Token {
	private boolean bool;

	public BooleanToken(boolean bool) {
		this.bool = bool;
	}

	public boolean getBoolean() {
		return bool;
	}

	@Override
	public String toString() {
		return "Boolean: " + this.bool;
	}
}
