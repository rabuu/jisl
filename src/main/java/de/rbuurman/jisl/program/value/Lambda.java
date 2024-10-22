package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.evaluation.ValueApplicable;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Lambda extends ValueApplicable {
    private final Multiple<VariableName> variables;
    private final Expression expression;

    public Lambda(Multiple<VariableName> variables, Expression expression, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.variables = variables;
        this.expression = expression;
    }

    @Override
    public Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException {
        if (this.variables.size() != arguments.size()) {
            throw new EvaluationException(
                    "Lambda expected " + this.variables.size() + " arguments but got " + arguments.size(),
                    this.getSourcePosition());
        }

        var localEnvironment = new Environment();
        for (VariableName variable : this.variables) {
            Value arg = arguments.poll();
            localEnvironment.addDefinition(variable, arg);
        }

        var mergedEnvironment = Environment.merge(environment, localEnvironment);
        Value evaluated = this.expression.evaluate(mergedEnvironment);

        if (evaluated instanceof Lambda) {
            return evaluated.replace(localEnvironment).evaluate(mergedEnvironment);
        }

        return evaluated;
    }

    @Override
    public String toString() {
        return "(λ " + this.variables + " " + this.expression + ")";
    }

    @Override
    public Expression replace(VariableName variable, Value value) {
        if (!this.variables.contains(variable)) {
            Expression newExpr = this.expression.replace(variable, value);
            return new Lambda(Multiple.copy(this.variables), newExpr, this.getSourcePosition());
        }
        return this;
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
