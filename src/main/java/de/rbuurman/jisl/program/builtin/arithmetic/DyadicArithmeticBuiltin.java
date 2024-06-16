package de.rbuurman.jisl.program.builtin.arithmetic;

import java.util.Queue;

import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.builtin.Builtin;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.primitive.NumberPrimitive;

/**
 * Add
 */
public abstract class DyadicArithmeticBuiltin extends Builtin implements Value, Applicable {

    protected abstract double operation(double x, double y);

    protected double singleValue(double x) {
        return x;
    }

    @Override
    public Value apply(Queue<Value> arguments) throws EvaluationException {
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
