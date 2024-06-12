package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class BooleanToken extends StateToken<Boolean> {
	public BooleanToken(boolean bool, SourcePosition sourcePosition) {
		super(bool, sourcePosition);
	}
}
