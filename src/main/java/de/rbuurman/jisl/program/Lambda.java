package de.rbuurman.jisl.program;

import java.util.Queue;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public record Lambda(Queue<Identifier> identifiers, Expression expression) implements Value, Applicable {

    @Override
    public Value apply(Queue<Value> arguments, Environment environment) throws EvaluationException {
        if (this.identifiers().size() != arguments.size()) {
            throw new EvaluationException("Lambda definition doesn't match given arguments");
        }

        var localEnvironment = new Environment();
        for (Identifier ident : this.identifiers()) {
            Value arg = arguments.poll();
            localEnvironment.addDefinition(ident, arg);
        }

        var mergedEnvironment = Environment.merge(environment, localEnvironment);
        return this.expression().evaluate(mergedEnvironment);
    }

    @Override
    public final String toString() {
        return "(lambda (...) ...)";
    }

}
