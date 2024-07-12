package de.rbuurman.jisl.program.builtin.arithmetic.basic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Subtraction
 */
public final class Subtraction extends BasicArithmeticOperation {

    public Subtraction(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected double singleValue(double x) {
        return -x;
    }

    @Override
    protected double operation(double x, double y) {
        return x - y;
    }

}
