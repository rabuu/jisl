package de.rbuurman.jisl.program.builtin.arithmetic.operation;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Subtraction
 */
public final class Subtraction extends BasicArithmeticOperation {

    public Subtraction(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x - y;
    }

}
