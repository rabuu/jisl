package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsNumber extends IsSomething {
    public IsNumber(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof NumberPrimitive;
    }
}
