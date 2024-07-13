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

public final class Append extends ValueApplicable {
    public Append(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    private static List append(List base, List addition) {
        if (base instanceof ConsList consList) {
            return new ConsList(consList.getHead(), append(consList.getTail(), addition), consList.getSourcePosition());
        }

        return addition;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        List list = new EmptyList(this.getSourcePosition());

        for (var arg : arguments) {
            if (!(arg instanceof List argList)) {
                throw new EvaluationException("Argument of " + this + " must be a list but is " + arg, arg.getSourcePosition());
            }

            list = append(list, argList);
        }

        return list;
    }
}
