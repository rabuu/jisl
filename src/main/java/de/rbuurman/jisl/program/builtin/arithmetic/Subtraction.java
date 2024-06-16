package de.rbuurman.jisl.program.builtin.arithmetic;

/**
 * Subtraction
 */
public final class Subtraction extends DyadicArithmeticBuiltin {

    @Override
    protected double operation(double x, double y) {
        return x - y;
    }

}
