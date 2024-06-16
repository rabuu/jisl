package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public interface Expression extends ProgramElement {
    public Value evaluate(Environment environment) throws EvaluationException;
}
