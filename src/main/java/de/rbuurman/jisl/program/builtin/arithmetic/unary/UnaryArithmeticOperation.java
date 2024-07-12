package de.rbuurman.jisl.program.builtin.arithmetic.unary;

import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class UnaryArithmeticOperation<OUTPUT extends Value> extends ValueApplicable {
    public UnaryArithmeticOperation(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected abstract OUTPUT operation(double x);

    protected boolean argumentIsValid(double arg) {
        return true;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        var val = arguments.poll();
        if (!(val instanceof NumberPrimitive num)) {
            throw new EvaluationException(this + "expects a numerical argument", val.getSourcePosition());
        }

        if (!argumentIsValid(num.getInner())) {
            throw new EvaluationException("Number " + num + " is invalid for " + this, val.getSourcePosition());
        }

        return (Value) operation(num.getInner()).withSourcePosition(this.getSourcePosition());
    }
}
