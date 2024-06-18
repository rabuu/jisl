package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class Value extends Expression {

    public Value(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return this;
    }

    @Override
    public abstract boolean equals(Object obj);

}
