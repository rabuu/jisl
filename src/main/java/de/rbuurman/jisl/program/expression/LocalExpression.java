package de.rbuurman.jisl.program.expression;

import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;

/**
 * LocalExpression
 */
public final class LocalExpression extends Expression {
    private Multiple<Definition> definitions;
    private Expression expression;

    public LocalExpression(Multiple<Definition> definitions, Expression expression) {
        this.definitions = definitions;
        this.expression = expression;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        Environment mergedEnvironment = environment;
        Environment localEnvironment = new Environment();
        for (Definition def : this.definitions) {
            final var variable = def.getVariable();
            final var value = def.getExpression().evaluate(mergedEnvironment);

            localEnvironment.addDefinition(variable, value);
            mergedEnvironment = Environment.merge(environment, localEnvironment);
        }

        return this.expression.evaluate(mergedEnvironment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final LocalExpression other = (LocalExpression) obj;
        return this.definitions.equals(other.definitions) && this.expression.equals(other.expression);
    }

    @Override
    public String toString() {
        return "(local ([...] ...) ...)";
    }

}