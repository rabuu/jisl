package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;

public final class SExpression extends Expression {
    private Expression function;
    private Multiple<Expression> arguments;

    public SExpression(Expression function, Multiple<Expression> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        Value function = this.function.evaluate(environment);
        if (!(function instanceof Applicable)) {
            throw new EvaluationException(function + " is not applicable");
        }
        Applicable applicable = (Applicable) function;

        Multiple<Value> arguments = new Multiple<>();
        for (Expression arg : this.arguments) {
            arguments.add(arg.evaluate(environment));
        }

        return applicable.apply(arguments, environment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SExpression other = (SExpression) obj;
        return this.function.equals(other.function) && this.arguments.equals(other.arguments);
    }
}
