package de.rbuurman.jisl.program.builtin.arithmetic.basic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Addition
 */
public final class Minimum extends BasicArithmeticOperation {

    public Minimum(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return Math.min(x, y);
    }

}
