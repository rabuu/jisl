package de.rbuurman.jisl.program.value.builtin;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Equality (eq?)
 */
public final class Equality extends Applicable {

    public Equality(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expects exactly two arguments", this.getSourcePosition());
        }

        final var arg1 = arguments.poll();
        final var arg2 = arguments.poll();

        return new BooleanPrimitive(arg1.equals(arg2));
    }

    @Override
    public String toString() {
        return "eq?";
    }
}
