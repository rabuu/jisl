package de.rbuurman.jisl.parsing.lexing.token;

public final class IdentToken extends Token {
	private String ident;

	public IdentToken(String ident) {
		this.ident = ident;
	}

	public String getIdent() {
		return ident;
	}

	@Override
	public String toString() {
		return "Identifier: " + this.ident;
	}

}
