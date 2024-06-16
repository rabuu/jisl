package de.rbuurman.jisl.program;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.PeekableQueue;

public final class Program extends PeekableQueue<ProgramElement> {

    public Queue<Value> run() throws EvaluationException {
        Queue<Value> results = new LinkedList<>();
        Environment environment = new Environment();

        for (ProgramElement element : this.elements) {
            if (element instanceof Definition) {
                var definition = (Definition) element;

                Identifier identifier = definition.identifier();
                Value value = definition.expression().evaluate(environment);

                environment.addDefinition(identifier, value);
            } else if (element instanceof Expression) {
                var expression = (Expression) element;
                results.add(expression.evaluate(environment));
            }
        }

        return results;
    }
}
