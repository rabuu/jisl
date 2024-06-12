package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class SimpleToken extends StateToken<SimpleTokenType> {
	public SimpleToken(SimpleTokenType type, SourcePosition sourcePosition) {
		super(type, sourcePosition);
	}

	@Override
	public boolean exit() {
		return this.getState() == SimpleTokenType.EOF;
	}
}
