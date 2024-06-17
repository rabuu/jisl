package de.rbuurman.jisl.program.builtin.logic;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;

/**
 * And
 */
public final class And extends Applicable {

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {

        if (arguments.size() < 2) {
            throw new EvaluationException(this + " expects at least two arguments", this.getSourcePosition());
        }

        for (var arg : arguments) {
            if (!(arg instanceof BooleanPrimitive)) {
                throw new EvaluationException("Argument must be boolean", arg.getSourcePosition());
            }

            final BooleanPrimitive b = (BooleanPrimitive) arg;

            if (!b.getInner()) {
                return new BooleanPrimitive(false);
            }
        }

        return new BooleanPrimitive(true);
    }

}
