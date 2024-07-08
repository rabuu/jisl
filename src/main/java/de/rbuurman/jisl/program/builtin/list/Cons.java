package de.rbuurman.jisl.program.builtin.list;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Cons
 */
public final class Cons extends Applicable {

    public Cons(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expected exactly two arguments", this.getSourcePosition());
        }

        final var head = arguments.poll();
        final var tailValue = arguments.poll();

        if (!(tailValue instanceof List tail)) {
            throw new EvaluationException("Second argument of " + this + " must be a list", this.getSourcePosition());
        }

        return new ConsList(head, tail, this.getSourcePosition());
    }

    @Override
    public String toString() {
        return "cons";
    }
}
