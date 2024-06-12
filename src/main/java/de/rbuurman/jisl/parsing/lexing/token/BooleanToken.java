package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;
import de.rbuurman.jisl.elements.value.BooleanValue;
import de.rbuurman.jisl.elements.value.Value;

public final class BooleanToken extends StateToken<Boolean> implements ValueToken {
	public BooleanToken(boolean bool, SourcePosition sourcePosition) {
		super(bool, sourcePosition);
	}

	@Override
	public Value toValue() {
		return new BooleanValue(this.getState());
	}
}
