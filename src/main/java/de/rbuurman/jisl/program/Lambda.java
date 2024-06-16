package de.rbuurman.jisl.program;

import java.util.Queue;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public record Lambda(Queue<Identifier> identifiers, Expression expression) implements Value {

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return this;
    }
}
