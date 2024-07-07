package de.rbuurman.jisl.program.builtin.struct;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Struct;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * IsStruct
 */
public final class IsStruct extends Applicable {
    private final VariableName struct;

    public IsStruct(final VariableName struct, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.struct = struct;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        final var val = arguments.poll();
        if (!(val instanceof Struct structInstance)) {
            return (Value) new BooleanPrimitive(false).withSourcePosition(this.getSourcePosition());
        }

        final boolean is = structInstance.getName().equals(this.struct);
        return (Value) new BooleanPrimitive(is).withSourcePosition(this.getSourcePosition());
    }

    @Override
    public String toString() {
        return this.struct.getInner() + "?";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IsStruct other = (IsStruct) obj;
        return this.struct.equals(other.struct);
    }
}
