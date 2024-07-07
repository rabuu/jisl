package de.rbuurman.jisl.program.builtin.arithmetic;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Multiplication
 */
public final class Multiplication extends ArithmeticOperation {

    public Multiplication(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected double operation(double x, double y) {
        return x * y;
    }

}
