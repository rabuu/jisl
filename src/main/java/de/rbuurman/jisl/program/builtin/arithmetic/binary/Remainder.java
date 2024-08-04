package de.rbuurman.jisl.program.builtin.arithmetic.binary;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public class Remainder extends BinaryArithmeticOperation<NumberPrimitive> {
    public Remainder(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean argumentIsValid(double arg) {
        return (arg % 1) == 0;
    }

    @Override
    protected NumberPrimitive operation(double x, double y) {
	int xInt = (int) x;
	int yInt = (int) y;
        return new NumberPrimitive((double) (xInt % yInt));
    }
}
