package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsInteger extends IsSomething {
    public IsInteger(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        if (value instanceof NumberPrimitive number) {
            return number.getInner() % 1 == 0;
        }
        return false;
    }
}
