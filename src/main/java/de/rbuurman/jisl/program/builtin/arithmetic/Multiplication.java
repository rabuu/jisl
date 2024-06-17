package de.rbuurman.jisl.program.builtin.arithmetic;

/**
 * Multiplication
 */
public final class Multiplication extends DyadicArithmeticBuiltin {

    @Override
    protected double operation(double x, double y) {
        return x * y;
    }

}