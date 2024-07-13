package de.rbuurman.jisl.program.value.list;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * List
 */
public abstract class List extends Value {

    public List(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    public abstract Multiple<Value> toValues();

}
