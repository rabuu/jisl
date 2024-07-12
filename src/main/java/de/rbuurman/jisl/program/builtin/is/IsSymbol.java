package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.program.value.primitive.SymbolPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsSymbol extends IsSomething {
    public IsSymbol(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof SymbolPrimitive;
    }
}
