package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.primitive.Primitive;

public final class PrimitiveToken<P extends Primitive<?>> extends StateToken<P> {
	public PrimitiveToken(P primitive) {
		super(primitive);
	}
}
