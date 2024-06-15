package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.primitive.Primitive;

public final class PrimitiveToken<P extends Primitive<?>> extends Token<P> {
	public PrimitiveToken(P primitive) {
		super(primitive);
	}

	public P getPrimitive() {
		return this.getState();
	}
}
