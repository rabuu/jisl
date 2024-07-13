package de.rbuurman.jisl.program.builtin.list;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.program.value.list.EmptyList;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class MakeList extends ValueApplicable {
    public MakeList(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (arguments.size() != 2) {
            throw new EvaluationException(this + " expects exactly two arguments but got " + arguments.size(), this.getSourcePosition());
        }

        final var firstValue = arguments.poll();
        if (!(firstValue instanceof NumberPrimitive num)) {
            throw new EvaluationException("First argument of " + this + " must be a natural number but is " + firstValue, firstValue.getSourcePosition());
        }

        if (num.getInner() % 1 != 0 || num.getInner() <= 0) {
            throw new EvaluationException("First argument of " + this + " must be a natural number but is " + num, num.getSourcePosition());
        }

        final int i = num.getInner().intValue();

        final var secondValue = arguments.poll();

        List list = new ConsList(secondValue, new EmptyList(this.getSourcePosition()), this.getSourcePosition());

        for (int j = 0; j < i - 1; ++j) {
            list = new ConsList(secondValue, list, list.getSourcePosition());
        }

        return list;
    }
}
