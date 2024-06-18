package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.value.primitive.Primitive;

public final class PrimitiveToken<P extends Primitive<?>> extends Token<P> {
	public PrimitiveToken(P primitive) {
		super(primitive);
	}
}
