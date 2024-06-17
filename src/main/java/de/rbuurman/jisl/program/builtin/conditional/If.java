package de.rbuurman.jisl.program.builtin.conditional;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.SimpleApplicable;
import de.rbuurman.jisl.program.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;

/**
 * If
 */
public final class If extends SimpleApplicable {

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 3) {
            throw new EvaluationException("An if expression needs a predicate, a then-clause and an else-clause");
        }

        final Value predValue = arguments.poll();
        if (!(predValue instanceof BooleanPrimitive)) {
            throw new EvaluationException("The predicate " + predValue + " is no Boolean");
        }
        final BooleanPrimitive pred = (BooleanPrimitive) predValue;

        final Value thenClause = arguments.poll();
        final Value elseClause = arguments.poll();

        if (pred.getInner()) {
            return thenClause;
        } else {
            return elseClause;
        }
    }

}