package de.rbuurman.jisl.program.builtin.arithmetic.binary;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class BinaryArithmeticOperation<OUTPUT extends Value> extends Applicable {
    public BinaryArithmeticOperation(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected abstract OUTPUT operation(double x, double y);

    protected boolean argumentIsValid(double arg) {
        return true;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        var val1 = arguments.poll();
        if (!(val1 instanceof NumberPrimitive num1)) {
            throw new EvaluationException(this + "expects a numerical argument", val1.getSourcePosition());
        }

        if (!argumentIsValid(num1.getInner())) {
            throw new EvaluationException("Number " + num1 + " is invalid for " + this, val1.getSourcePosition());
        }

        var val2 = arguments.poll();
        if (!(val2 instanceof NumberPrimitive num2)) {
            throw new EvaluationException(this + "expects a numerical argument", val2.getSourcePosition());
        }

        if (!argumentIsValid(num2.getInner())) {
            throw new EvaluationException("Number " + num2 + " is invalid for " + this, val2.getSourcePosition());
        }

        return (Value) operation(num1.getInner(), num2.getInner()).withSourcePosition(this.getSourcePosition());
    }
}
