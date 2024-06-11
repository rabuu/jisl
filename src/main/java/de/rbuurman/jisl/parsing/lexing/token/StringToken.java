package de.rbuurman.jisl.parsing.lexing.token;

public final class StringToken implements Token {
	private String string;

	public StringToken(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
