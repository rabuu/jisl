package de.rbuurman.jisl.program.builtin.arithmetic;

/**
 * Division
 */
public final class Division extends DyadicArithmeticBuiltin {

    @Override
    protected double operation(double x, double y) {
        return x / y;
    }

    @Override
    protected double singleValue(double x) {
        return 1 / x;
    }

}
