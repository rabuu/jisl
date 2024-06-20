package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Lambda extends Applicable {
    private Multiple<VariableName> variables;
    private Expression expression;

    public Lambda(Multiple<VariableName> variables, Expression expression, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.variables = variables;
        this.expression = expression;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (this.variables.size() != arguments.size()) {
            throw new EvaluationException("Lambda definition doesn't match given arguments", this.getSourcePosition());
        }

        var localEnvironment = new Environment();
        for (VariableName variable : this.variables) {
            Value arg = arguments.poll();
            localEnvironment.addDefinition(variable, arg);
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
        return this.variables.equals(other.variables) && this.expression.equals(other.expression);
    }

}
