package de.rbuurman.jisl.parsing.lexing.token;

public final class PrimitiveToken extends Token {
	private PrimitiveTokenType type;

	public PrimitiveToken(PrimitiveTokenType type) {
		this.type = type;
	}

	@Override
	public boolean exit() {
		return this.type == PrimitiveTokenType.EOF;
	}

	@Override
	public String toString() {
		return "Primitive: " + this.type;
	}
}
