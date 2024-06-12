package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class StringToken extends StateToken<String> {
	public StringToken(String string, SourcePosition sourcePosition) {
		super(string, sourcePosition);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}
}
