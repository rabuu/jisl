package de.rbuurman.jisl.program.builtin.logic;

import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * And
 */
public final class And extends Applicable {

    public And(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value lazy_apply(Multiple<Expression> arguments, Environment environment) throws EvaluationException {

        if (arguments.size() < 2) {
            throw new EvaluationException(this + " expects at least two arguments", this.getSourcePosition());
        }

        for (var arg : arguments) {
            final Value val = arg.evaluate(environment);
            if (!(val instanceof BooleanPrimitive b)) {
                throw new EvaluationException("Argument of " + this + " must be boolean", this.getSourcePosition());
            }

            if (!b.getInner()) {
                return new BooleanPrimitive(false);
            }
        }

        return new BooleanPrimitive(true);
    }

}
