package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.value.Struct;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsProcedure extends IsSomething {
    public IsProcedure(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof Applicable;
    }
}
