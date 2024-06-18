package de.rbuurman.jisl.program.value.builtin.logic;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;

/**
 * If
 */
public final class If extends Applicable {

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 3) {
            throw new EvaluationException("An if expression needs a predicate, a then-clause and an else-clause");
        }

        final Value predValue = arguments.poll();
        if (!(predValue instanceof BooleanPrimitive pred)) {
            throw new EvaluationException("The predicate " + predValue + " is no Boolean");
        }

        final Value thenClause = arguments.poll();
        final Value elseClause = arguments.poll();

        if (pred.getInner()) {
            return thenClause;
        } else {
            return elseClause;
        }
    }

}
