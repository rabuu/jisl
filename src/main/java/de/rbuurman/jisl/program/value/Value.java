package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;

public abstract class Value extends Expression {

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return this;
    }

    @Override
    public abstract boolean equals(Object obj);

}
