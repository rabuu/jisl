package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Struct;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.StringPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsStruct extends IsSomething {
    public IsStruct(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof Struct;
    }
}
