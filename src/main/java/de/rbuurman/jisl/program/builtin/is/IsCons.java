package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsCons extends IsSomething {
    public IsCons(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof ConsList;
    }
}
