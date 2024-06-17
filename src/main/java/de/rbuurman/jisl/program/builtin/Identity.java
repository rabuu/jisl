package de.rbuurman.jisl.program.builtin;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.SimpleApplicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Identity
 */
public final class Identity extends SimpleApplicable {

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException("The identity expects a single argument");
        }

        return arguments.poll();
    }

}
