package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsBoolean extends IsSomething {
    public IsBoolean(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof BooleanPrimitive;
    }
}
