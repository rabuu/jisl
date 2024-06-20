package de.rbuurman.jisl.program.value.list;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * EmptyList
 */
public final class EmptyList extends List {

    public EmptyList(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        return getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        return "'()";
    }

}
