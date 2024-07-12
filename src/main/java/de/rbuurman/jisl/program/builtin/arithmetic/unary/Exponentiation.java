package de.rbuurman.jisl.program.builtin.arithmetic.unary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Exponentiation extends UnaryArithmeticOperation<NumberPrimitive> {
    public Exponentiation(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected NumberPrimitive operation(double x) {
        return new NumberPrimitive(Math.exp(x));
    }
}
