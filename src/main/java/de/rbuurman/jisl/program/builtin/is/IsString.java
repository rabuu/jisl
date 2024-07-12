package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.CharacterPrimitive;
import de.rbuurman.jisl.program.value.primitive.StringPrimitive;
import de.rbuurman.jisl.utils.SourcePosition;

public final class IsString extends IsSomething {
    public IsString(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    protected boolean is(Value value) {
        return value instanceof StringPrimitive;
    }
}
