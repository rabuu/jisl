package de.rbuurman.jisl.program.builtin.arithmetic.unary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Random extends UnaryArithmeticOperation<NumberPrimitive> {
    public Random(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean argumentIsValid(double arg) {
        return arg > 0 && (arg % 1) == 0;
    }

    @Override
    protected NumberPrimitive operation(double x) {
        java.util.Random rand = new java.util.Random();
        return new NumberPrimitive((double) rand.nextInt((int) x));
    }
}
