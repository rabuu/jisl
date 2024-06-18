package de.rbuurman.jisl.program.value.builtin.arithmetic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Subtraction
 */
public final class Subtraction extends DyadicArithmeticBuiltin {

    public Subtraction(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x - y;
    }

}
