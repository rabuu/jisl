package de.rbuurman.jisl.program.builtin.list;

import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Apply extends ValueApplicable {
    public Apply(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expects exactly one argument but got " + arguments.size(), this.getSourcePosition());
        }

        final var firstValue = arguments.poll();
        if (!(firstValue instanceof Applicable applicable)) {
            throw new EvaluationException("First argument of " + this + " must be a function but is " + firstValue, firstValue.getSourcePosition());
        }

        final var secondValue = arguments.poll();
        if (!(secondValue instanceof List list)) {
            throw new EvaluationException("Second argument of " + this + " must be a list but is " + secondValue, secondValue.getSourcePosition());
        }

        Multiple<Expression> args = new Multiple<>();
        for (var val : list.toValues()) {
            args.add(val);
        }

        return applicable.lazy_apply(args, environment);
    }
}
