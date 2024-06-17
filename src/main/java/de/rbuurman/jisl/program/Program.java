package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.PeekableQueue;
import de.rbuurman.jisl.utils.Multiple;

public final class Program extends PeekableQueue<ProgramElement> {

    public Multiple<Value> run() throws EvaluationException {
        Multiple<Value> results = new Multiple<>();
        Environment environment = new Environment();

        for (ProgramElement element : this.elements) {
            if (element instanceof Definition) {
                var definition = (Definition) element;

                Identifier identifier = definition.getIdentifier();
                Value value = definition.getExpression().evaluate(environment);

                environment.addDefinition(identifier, value);
            } else if (element instanceof Expression) {
                var expression = (Expression) element;
                results.add(expression.evaluate(environment));
            }
        }

        return results;
    }
}
