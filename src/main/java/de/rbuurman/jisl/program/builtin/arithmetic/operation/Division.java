package de.rbuurman.jisl.program.builtin.arithmetic.operation;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Division
 */
public final class Division extends ArithmeticOperation {

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
