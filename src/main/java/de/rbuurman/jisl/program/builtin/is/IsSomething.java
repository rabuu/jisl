package de.rbuurman.jisl.program.builtin.is;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class IsSomething extends ValueApplicable {

    public IsSomething(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected abstract boolean is(Value value);

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        final var val = arguments.poll();

        return (Value) new BooleanPrimitive(is(val)).withSourcePosition(this.getSourcePosition());
    }
}
