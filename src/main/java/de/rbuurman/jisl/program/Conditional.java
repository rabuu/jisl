package de.rbuurman.jisl.program;

import java.util.Optional;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Conditional
 */
public final class Conditional extends Expression {
    private Multiple<Expression[]> conditionals;
    private Optional<Expression> elseClause;

    public Conditional(Multiple<Expression[]> conditionals, Optional<Expression> elseClause) {
        this.conditionals = conditionals;
        this.elseClause = elseClause;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        for (Expression[] cond : this.conditionals) {
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

        if (this.elseClause.isPresent()) {
            return this.elseClause.get().evaluate(environment);
        }

        throw new EvaluationException("No conditional clauses evaluated to #true");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Conditional other = (Conditional) obj;

        return this.conditionals.equals(other.conditionals) && this.elseClause.equals(other.elseClause);
    }

    @Override
    public String toString() {
        return "(cond [...])";
    }
}
