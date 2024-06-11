package de.rbuurman.jisl.parsing.lexing.token;

public final class IntegerToken extends Token {
	private int integer;

	public IntegerToken(int integer) {
		this.integer = integer;
	}

	public int getInteger() {
		return integer;
	}

	@Override
	public String toString() {
		return "Integer: " + this.integer;
	}
}
