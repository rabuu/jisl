package de.rbuurman.jisl.program.builtin.arithmetic.comparison;

import de.rbuurman.jisl.utils.SourcePosition;

public final class Less extends ArithmeticComparison {
    public Less(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean comparison(double x, double y) {
        return x < y;
    }
}
