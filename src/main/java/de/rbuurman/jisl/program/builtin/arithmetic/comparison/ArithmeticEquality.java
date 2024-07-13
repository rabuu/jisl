package de.rbuurman.jisl.program.builtin.arithmetic.comparison;

import de.rbuurman.jisl.utils.SourcePosition;

public final class ArithmeticEquality extends ArithmeticComparison {
    @Override
    protected boolean allowSingleArgument() {
        return true;
    }

    public ArithmeticEquality(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean comparison(double x, double y) {
        return x == y;
    }
}
