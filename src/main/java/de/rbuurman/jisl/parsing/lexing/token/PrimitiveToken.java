package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.primitive.Primitive;

public final class PrimitiveToken<P extends Primitive<?>> extends StateToken<P> {
	public PrimitiveToken(P primitive) {
		super(primitive);
	}
}
