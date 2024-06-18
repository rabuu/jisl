package de.rbuurman.jisl.program.value.builtin;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Identity
 */
public final class Identity extends Applicable {

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException("The identity expects a single argument");
        }

        return arguments.poll();
    }

}
