package de.rbuurman.jisl.program.value.builtin.logic;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * And
 */
public final class And extends Applicable {

    public And(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {

        if (arguments.size() < 2) {
            throw new EvaluationException(this + " expects at least two arguments", this.getSourcePosition());
        }

        for (var arg : arguments) {
            if (!(arg instanceof BooleanPrimitive b)) {
                throw new EvaluationException("Argument must be boolean", arg.getSourcePosition());
            }

            if (!b.getInner()) {
                return new BooleanPrimitive(false);
            }
        }

        return new BooleanPrimitive(true);
    }

}
