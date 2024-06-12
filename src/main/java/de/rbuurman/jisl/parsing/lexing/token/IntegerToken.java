package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class IntegerToken extends StateToken<Integer> {
	public IntegerToken(int integer, SourcePosition sourcePosition) {
		super(integer, sourcePosition);
	}
}
