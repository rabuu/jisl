package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;
import de.rbuurman.jisl.parsing.value.Value;
import de.rbuurman.jisl.parsing.value.IntegerValue;

public final class IntegerToken extends StateToken<Integer> implements ValueToken {
	public IntegerToken(int integer, SourcePosition sourcePosition) {
		super(integer, sourcePosition);
	}

	@Override
	public Value toValue() {
		return new IntegerValue(this.getState());
	}
}
