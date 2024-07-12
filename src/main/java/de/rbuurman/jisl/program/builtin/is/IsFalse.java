package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsFalse extends IsSomething {
    public IsFalse(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        if (value instanceof BooleanPrimitive bool) {
            return bool.getInner();
        }
        return false;
    }
}
