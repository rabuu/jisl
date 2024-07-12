package de.rbuurman.jisl.program.builtin.arithmetic.basic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Addition
 */
public final class Maximum extends BasicArithmeticOperation {

    public Maximum(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return Math.max(x, y);
    }

}
