package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Struct
 */
public final class Struct extends Value {
    private final VariableName name;
    private final Multiple<Value> fields;

    public Struct(final VariableName name, final Multiple<Value> fields, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.name = name;
        this.fields = fields;
    }

    public VariableName getName() {
        return this.name;
    }

    public Value getFieldAt(final int n) throws EvaluationException {
        if (n >= this.fields.size() || n < 0) {
            throw new EvaluationException("Cannot get field at position " + n, this.getSourcePosition());
        }

        var copy = Multiple.copy(this.fields);
        var field = copy.poll();
        for (int i = 0; i < n; ++i) {
            field = copy.poll();
        }
        return field;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Struct other = (Struct) obj;
        return this.name.equals(other.name) && this.fields.equals(other.fields);
    }

    @Override
    public String toString() {
        return "<" + this.name.getInner() + " " + this.fields + ">";
    }
}
