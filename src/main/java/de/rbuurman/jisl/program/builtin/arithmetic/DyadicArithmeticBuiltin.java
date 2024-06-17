package de.rbuurman.jisl.program.builtin.arithmetic;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Add
 */
public abstract class DyadicArithmeticBuiltin extends Applicable {

    protected abstract double operation(double x, double y);

    protected double singleValue(double x) {
        return x;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() < 1) {
            throw new EvaluationException(this + " expects at least one argument");
        }

        final var firstArgument = arguments.poll();
        if (!(firstArgument instanceof NumberPrimitive)) {
            throw new EvaluationException("Arguments of " + this + " must be numerical");
        }
        final double firstValue = ((NumberPrimitive) firstArgument).getInner();

        if (arguments.isEmpty()) {
            return new NumberPrimitive(singleValue(firstValue));
        }

        double num = firstValue;

        for (Value arg : arguments) {
            if (arg instanceof NumberPrimitive) {
                num = operation(num, ((NumberPrimitive) arg).getInner());
            } else {
                throw new EvaluationException("Arguments of " + this + " must be numerical");
            }
        }

        return new NumberPrimitive(num);

    }

}
