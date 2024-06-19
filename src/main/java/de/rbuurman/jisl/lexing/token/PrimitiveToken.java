package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.value.primitive.Primitive;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * A PrimitiveToken is a Token encapsulating a primitive type
 */
public final class PrimitiveToken<P extends Primitive<?>> extends Token<P> {
	public PrimitiveToken(P primitive, SourcePosition sourcePosition) {
		super(primitive, sourcePosition);
	}

	public Primitive<?> toPrimitive() {
		return (Primitive<?>) this.getState().withSourcePosition(this.getSourcePosition());
	}
}
