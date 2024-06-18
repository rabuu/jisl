package de.rbuurman.jisl.program.value.builtin.arithmetic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Addition
 */
public final class Addition extends DyadicArithmeticBuiltin {

    public Addition(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x + y;
    }

}
