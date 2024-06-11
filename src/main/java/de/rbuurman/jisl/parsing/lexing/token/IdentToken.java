package de.rbuurman.jisl.parsing.lexing.token;

public final class IdentToken implements Token {
	private String ident;

	public IdentToken(String ident) {
		this.ident = ident;
	}

	public String getIdent() {
		return ident;
	}
}
