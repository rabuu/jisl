package de.rbuurman.jisl.parsing.lexing.token;

public final class FloatToken extends StateToken<Float> {
	public FloatToken(float fl) {
		super(fl);
	}

	@Override
	public boolean compareState(Float cmp) {
		return Math.abs(this.getState() - cmp) < 0.0000001;
	}
}
