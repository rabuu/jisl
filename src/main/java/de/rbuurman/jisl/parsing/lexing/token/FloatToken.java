package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;
import de.rbuurman.jisl.parsing.value.FloatValue;
import de.rbuurman.jisl.parsing.value.Value;

public final class FloatToken extends StateToken<Float> implements ValueToken {
	public FloatToken(float fl, SourcePosition sourcePosition) {
		super(fl, sourcePosition);
	}

	@Override
	public boolean compareState(Float cmp) {
		return Math.abs(this.getState() - cmp) < 0.0000001;
	}

	@Override
	public Value toValue() {
		return new FloatValue(this.getState());
	}
}
