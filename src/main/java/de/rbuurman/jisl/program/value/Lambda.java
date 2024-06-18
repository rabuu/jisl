package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.expression.Identifier;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.Multiple;

public final class Lambda extends Applicable {
    private Multiple<Identifier> identifiers;
    private Expression expression;

    public Lambda(Multiple<Identifier> identifiers, Expression expression) {
        this.identifiers = identifiers;
        this.expression = expression;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (this.identifiers.size() != arguments.size()) {
            throw new EvaluationException("Lambda definition doesn't match given arguments");
        }

        var localEnvironment = new Environment();
        for (Identifier ident : this.identifiers) {
            Value arg = arguments.poll();
            localEnvironment.addDefinition(ident, arg);
        }

        var mergedEnvironment = Environment.merge(environment, localEnvironment);
        return this.expression.evaluate(mergedEnvironment);
    }

    @Override
    public final String toString() {
        return "(lambda (...) ...)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Lambda other = (Lambda) obj;
        return this.identifiers.equals(other.identifiers) && this.expression.equals(other.expression);
    }

}
