package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public abstract class Expression extends ProgramElement {
    public abstract Value evaluate(Environment environment) throws EvaluationException;

    @Override
    public abstract boolean equals(Object obj);
}
