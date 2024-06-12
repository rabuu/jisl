package de.rbuurman.jisl.parsing.lexing.token;

public final class IdentToken extends StateToken<String> {
	public IdentToken(String ident) {
		super(ident);
	}
}
