package de.rbuurman.jisl.program.builtin.arithmetic.binary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public class Exponentiation extends BinaryArithmeticOperation<NumberPrimitive> {
    public Exponentiation(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected NumberPrimitive operation(double x, double y) {
        return new NumberPrimitive(Math.pow(x, y));
    }
}
