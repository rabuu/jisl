package de.rbuurman.jisl.program.builtin.arithmetic.comparison;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Abstract ArithmeticComparison (=, >, <)
 */
public abstract class ArithmeticComparison extends Applicable {
    public ArithmeticComparison(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    protected abstract boolean comparison(double x, double y);

    protected boolean allowSingleArgument() {
        return false;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() < 2) {
            if (!((arguments.size() == 1) && this.allowSingleArgument())) {
                throw new EvaluationException(this + " expects at least two argument", this.getSourcePosition());
            }
        }

        var prev = arguments.poll();
        if (!(prev instanceof NumberPrimitive prevNum)) {
            throw new EvaluationException(this + " expects numerical arguments", this.getSourcePosition());
        }

        for (var arg : arguments) {
            if (!(arg instanceof NumberPrimitive argNum)) {
                throw new EvaluationException(this + " expects numerical arguments", this.getSourcePosition());
            }

            if (!this.comparison(prevNum.getInner(), argNum.getInner())) {
                return (Value) new BooleanPrimitive(false).withSourcePosition(this.getSourcePosition());
            }

            prevNum = argNum;
        }

        return (Value) new BooleanPrimitive(true).withSourcePosition(this.getSourcePosition());
    }

}
