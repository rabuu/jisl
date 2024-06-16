package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public interface Value extends Expression {

    @Override
    default Value evaluate(Environment environment) throws EvaluationException {
        return this;
    }

}
