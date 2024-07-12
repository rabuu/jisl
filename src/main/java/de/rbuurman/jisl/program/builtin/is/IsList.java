package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.program.value.primitive.SymbolPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsList extends IsSomething {
    public IsList(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof List;
    }
}
