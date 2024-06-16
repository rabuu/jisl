package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public record Identifier(String identifier) implements Expression {

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return environment.getValue(this);
    }

    @Override
    public String toString() {
        return "`" + this.identifier() + "`";
    }

}
