package de.rbuurman.jisl.program.builtin.arithmetic.basic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Addition
 */
public final class Addition extends BasicArithmeticOperation {

    public Addition(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x + y;
    }

}
