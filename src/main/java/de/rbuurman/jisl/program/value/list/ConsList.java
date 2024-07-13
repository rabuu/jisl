package de.rbuurman.jisl.program.value.list;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * ConsList
 */
public final class ConsList extends List {
    private Value head;
    private List tail;

    public ConsList(Value head, List tail, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.head = head;
        this.tail = tail;
    }

    public Value getHead() {
        return head;
    }

    public List getTail() {
        return tail;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ConsList other = (ConsList) obj;
        return this.head.equals(other.head) && this.tail.equals(other.tail);
    }

    @Override
    public String toString() {
        return "(cons " + this.head + " " + this.tail + ")";
    }

    @Override
    public Multiple<Value> toValues() {
        var values =  this.tail.toValues();
        values.addFront(this.head);
        return values;
    }
}
