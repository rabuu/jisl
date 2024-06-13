package de.rbuurman.jisl.lexing.token;

public final class IdentToken extends StateToken<String> {
	public IdentToken(String ident) {
		super(ident);
	}
}
