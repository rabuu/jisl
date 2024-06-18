package de.rbuurman.jisl.program.value.builtin.arithmetic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Division
 */
public final class Division extends DyadicArithmeticBuiltin {

    public Division(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x / y;
    }

    @Override
    protected double singleValue(double x) {
        return 1 / x;
    }

}
