package de.rbuurman.jisl.program.builtin.struct;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Struct;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * FieldSelector
 */
public final class FieldSelector extends Applicable {
    private final VariableName struct;
    private final VariableName field;

    public FieldSelector(final VariableName struct, final VariableName field, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.struct = struct;
        this.field = field;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        final var val = arguments.poll();
        if (!(val instanceof Struct structInstance)) {
            throw new EvaluationException(this + " expects a struct instance of " + this.struct,
                    this.getSourcePosition());
        }

        final int fieldIndex = environment.getStructFieldIndex(struct, field);
        return structInstance.getFieldAt(fieldIndex);
    }

    @Override
    public String toString() {
        return this.struct.getInner() + "-" + this.field.getInner();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FieldSelector other = (FieldSelector) obj;
        return this.struct.equals(other.struct) && this.field.equals(other.field);
    }
}
