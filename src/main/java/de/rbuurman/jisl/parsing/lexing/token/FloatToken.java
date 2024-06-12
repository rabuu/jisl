package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class FloatToken extends StateToken<Float> {
	public FloatToken(float fl, SourcePosition sourcePosition) {
		super(fl, sourcePosition);
	}

	@Override
	public boolean compareState(Float cmp) {
		return Math.abs(this.getState() - cmp) < 0.0000001;
	}
}
