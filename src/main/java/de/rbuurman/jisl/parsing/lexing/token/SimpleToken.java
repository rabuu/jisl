package de.rbuurman.jisl.parsing.lexing.token;

public final class SimpleToken extends StateToken<SimpleTokenType> {
	public SimpleToken(SimpleTokenType type) {
		super(type);
	}

	@Override
	public boolean exit() {
		return this.getState() == SimpleTokenType.EOF;
	}

	@Override
	public String toString() {
		return this.getState().toString();
	}
}
