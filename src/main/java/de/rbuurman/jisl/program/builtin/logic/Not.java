package de.rbuurman.jisl.program.builtin.logic;

import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.primitive.BooleanPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Not
 */
public final class Not extends ValueApplicable {

    public Not(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {

        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects one argument, found " + arguments.size(), this.getSourcePosition());
        }

        final var arg = arguments.poll();
        if (!(arg instanceof BooleanPrimitive booleanArg)) {
            throw new EvaluationException(this + "expects boolean argument", this.getSourcePosition());
        }

        return new BooleanPrimitive(!booleanArg.getInner());
    }

}
