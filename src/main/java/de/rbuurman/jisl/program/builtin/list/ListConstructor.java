package de.rbuurman.jisl.program.builtin.list;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.ConsList;
import de.rbuurman.jisl.program.value.list.EmptyList;
import de.rbuurman.jisl.program.value.list.List;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class ListConstructor extends ValueApplicable {
    public ListConstructor(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        List list = new EmptyList(this.getSourcePosition());

        for (var arg : arguments.reversed()) {
            list = new ConsList(arg, list, list.getSourcePosition());
        }

        return list;
    }
}
