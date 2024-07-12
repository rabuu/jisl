package de.rbuurman.jisl.program.builtin.arithmetic.unary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Floor extends UnaryArithmeticOperation<NumberPrimitive> {
    public Floor(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected NumberPrimitive operation(double x) {
        return new NumberPrimitive(Math.floor(x));
    }
}
