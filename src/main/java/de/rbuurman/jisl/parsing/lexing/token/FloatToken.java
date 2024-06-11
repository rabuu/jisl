package de.rbuurman.jisl.parsing.lexing.token;

public final class FloatToken extends Token {
	private float fl;

	public FloatToken(float fl) {
		this.fl = fl;
	}

	public float getFloat() {
		return fl;
	}

	@Override
	public String toString() {
		return "Float: " + this.fl;
	}
}
