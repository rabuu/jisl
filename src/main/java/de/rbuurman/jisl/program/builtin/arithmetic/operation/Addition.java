package de.rbuurman.jisl.program.builtin.arithmetic.operation;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Addition
 */
public final class Addition extends ArithmeticOperation {

    public Addition(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x + y;
    }

}
