package de.rbuurman.jisl.program.expression;

import java.util.Optional;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Conditional
 */
public final class ConditionExpression extends Expression {
    private Multiple<Expression[]> conditionals;
    private Optional<Expression> elseClause;

    public ConditionExpression(
            Multiple<Expression[]> conditionals,
            Optional<Expression> elseClause,
            SourcePosition sourcePosition) {
        super(sourcePosition);
        this.conditionals = conditionals;
        this.elseClause = elseClause;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        for (Expression[] cond : this.conditionals) {
            final Value predValue = cond[0].evaluate(environment);
            if (!(predValue instanceof BooleanPrimitive pred)) {
                throw new EvaluationException("Conditional predicate " + cond[0] + " is no boolean",
                        cond[0].getSourcePosition());
            }
            final Expression expr = cond[1];

            if (pred.getInner()) {
                return expr.evaluate(environment);
            }
        }

        if (this.elseClause.isPresent()) {
            return this.elseClause.get().evaluate(environment);
        }

        throw new EvaluationException("No conditional clauses were true", this.getSourcePosition());
    }

    @Override
    public Expression replace(VariableName variable, Value value) {
        Multiple<Expression[]> newConditionals = new Multiple<>();
        for (var conditional : this.conditionals) {
            var cond0 = conditional[0].replace(variable, value);
            var cond1 = conditional[1].replace(variable, value);
            Expression[] conds = { cond0, cond1 };
            newConditionals.add(conds);
        }

        Optional<Expression> newElseClause = Optional.empty();
        if (this.elseClause.isPresent()) {
            newElseClause = Optional.of(this.elseClause.get().replace(variable, value));
        }

        return new ConditionExpression(newConditionals, newElseClause, this.getSourcePosition());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ConditionExpression other = (ConditionExpression) obj;

        return this.conditionals.equals(other.conditionals) && this.elseClause.equals(other.elseClause);
    }

    @Override
    public String toString() {
        return "(cond ...)";
    }
}
