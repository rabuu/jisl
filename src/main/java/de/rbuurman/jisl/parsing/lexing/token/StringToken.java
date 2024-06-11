package de.rbuurman.jisl.parsing.lexing.token;

public final class StringToken extends Token {
	private String string;

	public StringToken(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	@Override
	public String toString() {
		return "String: " + this.string;
	}
}
