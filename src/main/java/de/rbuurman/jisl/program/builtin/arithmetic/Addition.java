package de.rbuurman.jisl.program.builtin.arithmetic;

/**
 * Addition
 */
public final class Addition extends DyadicArithmeticBuiltin {

    @Override
    protected double operation(double x, double y) {
        return x + y;
    }

}
