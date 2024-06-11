package de.rbuurman.jisl.parsing.lexing.token;

public final class IntegerToken implements Token {
	private int integer;

	public IntegerToken(int integer) {
		this.integer = integer;
	}

	public int getInteger() {
		return integer;
	}
}
