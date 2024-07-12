package de.rbuurman.jisl.program.builtin.logic;

import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * If
 */
public final class If extends Applicable {

    public If(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value lazy_apply(Multiple<Expression> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 3) {
            throw new EvaluationException("An if expression needs a predicate, a then-clause and an else-clause, but got " + arguments.size() + " arguments",
                    this.getSourcePosition());
        }

        final Value predValue = arguments.poll().evaluate(environment);
        if (!(predValue instanceof BooleanPrimitive pred)) {
            throw new EvaluationException("Predicate " + predValue + " of if-statement is no boolean",
                    this.getSourcePosition());
        }

        final Value thenClause = arguments.poll().evaluate(environment);

        if (pred.getInner()) {
            return thenClause;
        } else {
            return arguments.poll().evaluate(environment);
        }
    }

}
