package de.rbuurman.jisl.program;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public record SExpression(Expression function, Queue<Expression> arguments) implements Expression {

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        Value function = this.function().evaluate(environment);
        if (!(function instanceof Applicable)) {
            throw new EvaluationException(function + " is not applicable");
        }
        Applicable applicable = (Applicable) function;

        Queue<Value> arguments = new LinkedList<>();
        for (Expression arg : this.arguments()) {
            arguments.add(arg.evaluate(environment));
        }

        return applicable.apply(arguments, environment);
    }
}
