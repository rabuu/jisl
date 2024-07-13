package de.rbuurman.jisl.program.builtin.list;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Cdr extends ValueApplicable {
    public Cdr(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 1) {
            throw new EvaluationException(this + " expects exactly one argument", this.getSourcePosition());
        }

        final var value = arguments.poll();
        if (!(value instanceof ConsList list)) {
            throw new EvaluationException("Argument of " + this + " must be a non-empty list but is " + value, value.getSourcePosition());
        }

        return list.getTail();
    }
}
