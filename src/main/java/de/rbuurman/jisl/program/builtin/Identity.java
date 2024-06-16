package de.rbuurman.jisl.program.builtin;

import java.util.Queue;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

/**
 * Identity
 */
public final class Identity extends Builtin implements Value, Applicable {

    @Override
    public Value apply(Queue<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException("The identity expects a single argument");
        }

        return arguments.poll();
    }

}
