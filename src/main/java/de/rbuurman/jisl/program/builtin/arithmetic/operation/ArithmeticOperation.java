package de.rbuurman.jisl.program.builtin.arithmetic.operation;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Abstract ArithmeticOperation
 */
public abstract class ArithmeticOperation extends Applicable {

    public ArithmeticOperation(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected abstract double operation(double x, double y);

    protected double singleValue(double x) {
        return x;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.isEmpty()) {
            throw new EvaluationException(this + " expects at least one argument", this.getSourcePosition());
        }

        final var firstArgument = arguments.poll();
        if (!(firstArgument instanceof NumberPrimitive firstValue)) {
            throw new EvaluationException("Arguments of " + this + " must be numerical", this.getSourcePosition());
        }

        if (arguments.isEmpty()) {
            return new NumberPrimitive(singleValue(firstValue.getInner()));
        }

        double num = firstValue.getInner();

        for (Value arg : arguments) {
            if (arg instanceof NumberPrimitive argNum) {
                num = operation(num, argNum.getInner());
            } else {
                throw new EvaluationException("Arguments of " + this + " must be numerical", getSourcePosition());
            }
        }

        return new NumberPrimitive(num);

    }

}
