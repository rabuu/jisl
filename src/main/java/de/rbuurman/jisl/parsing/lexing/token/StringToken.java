package de.rbuurman.jisl.parsing.lexing.token;

public final class StringToken extends StateToken<String> {
	public StringToken(String string) {
		super(string);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}
}
