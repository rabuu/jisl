package de.rbuurman.jisl.program.builtin.struct;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Struct;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * MakeStruct
 */
public final class MakeStruct extends ValueApplicable {
    private final VariableName struct;

    public MakeStruct(final VariableName struct, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.struct = struct;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        final int fieldNumber = environment.getStructFields(this.struct).size();
        if (arguments.size() != fieldNumber) {
            throw new EvaluationException("Expected " + fieldNumber + " arguments", this.getSourcePosition());
        }

        return new Struct(this.struct, arguments, this.getSourcePosition());
    }

    @Override
    public String toString() {
        return "make-" + this.struct.getInner();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MakeStruct other = (MakeStruct) obj;
        return this.struct.equals(other.struct);
    }
}
