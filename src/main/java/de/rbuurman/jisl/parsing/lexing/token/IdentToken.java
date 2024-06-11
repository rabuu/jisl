package de.rbuurman.jisl.parsing.lexing.token;

public final class IdentToken extends StateToken<String> {
	public IdentToken(String ident) {
		super(ident);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}
}
