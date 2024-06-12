package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class IdentToken extends StateToken<String> {
	public IdentToken(String ident, SourcePosition sourcePosition) {
		super(ident, sourcePosition);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}
}
