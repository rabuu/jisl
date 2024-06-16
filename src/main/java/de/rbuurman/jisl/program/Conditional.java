package de.rbuurman.jisl.program;

import java.util.Optional;
import java.util.Queue;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.primitive.BooleanPrimitive;

/**
 * Conditional
 */
public record Conditional(Queue<Expression[]> conditionals, Optional<Expression> elseClause) implements Expression {

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        for (Expression[] cond : this.conditionals()) {
            final Value predValue = cond[0].evaluate(environment);
            if (!(predValue instanceof BooleanPrimitive)) {
                throw new EvaluationException("Conditional predicate " + cond[0] + " is no Boolean");
            }
            final BooleanPrimitive pred = (BooleanPrimitive) predValue;
            final Expression expr = cond[1];

            if (pred.getInner()) {
                return expr.evaluate(environment);
            }
        }

        if (this.elseClause().isPresent()) {
            return this.elseClause().get().evaluate(environment);
        }

        throw new EvaluationException("No conditional clauses evaluated to #true");
    }
}
