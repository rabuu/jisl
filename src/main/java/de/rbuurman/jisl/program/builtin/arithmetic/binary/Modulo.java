package de.rbuurman.jisl.program.builtin.arithmetic.binary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public class Modulo extends BinaryArithmeticOperation<NumberPrimitive> {
    public Modulo(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean argumentIsValid(double arg) {
        return (arg % 1) == 0;
    }

    @Override
    protected NumberPrimitive operation(double x, double y) {
        return new NumberPrimitive((double) ((int) x % (int) y));
    }
}
