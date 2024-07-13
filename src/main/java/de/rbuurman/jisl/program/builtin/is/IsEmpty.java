package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.program.value.list.EmptyList;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsEmpty extends IsSomething {
    public IsEmpty(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof EmptyList;
    }
}
