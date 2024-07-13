package de.rbuurman.jisl.program.builtin.equality;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.program.value.primitive.Primitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Equality (eq?)
 */
public final class StrictEquality extends ValueApplicable {

    public StrictEquality(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expects exactly two arguments", this.getSourcePosition());
        }

        final var arg1 = arguments.poll();
        final var arg2 = arguments.poll();

        if (arg1 instanceof Primitive<?> p1 && arg2 instanceof Primitive<?> p2) {
            return new BooleanPrimitive(p1.getInner().equals(p2.getInner()));
        }

        return new BooleanPrimitive(arg1 == arg2);
    }

    @Override
    public String toString() {
        return "eq?";
    }
}
