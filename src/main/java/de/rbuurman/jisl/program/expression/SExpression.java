package de.rbuurman.jisl.program.expression;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Applicable;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

public final class SExpression extends Expression {
    private final Expression function;
    private final Multiple<Expression> arguments;

    public SExpression(Expression function, Multiple<Expression> arguments, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.function = function;
        this.arguments = arguments;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        Value function = this.function.evaluate(environment);
        if (!(function instanceof Applicable applicable)) {
            throw new EvaluationException(function + " is not applicable", function.getSourcePosition());
        }

        return applicable.lazy_apply(Multiple.copy(arguments), environment);
    }

    @Override
    public Expression replace(VariableName variable, Value value) {
        Expression newFunction = this.function.replace(variable, value);
        Multiple<Expression> newArguments = new Multiple<>();
        for (var arg : this.arguments) {
            newArguments.add(arg.replace(variable, value));
        }
        return new SExpression(newFunction, newArguments, this.getSourcePosition());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SExpression other = (SExpression) obj;
        return this.function.equals(other.function) && this.arguments.equals(other.arguments);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("(");
        builder.append(this.function);
        for (var arg : Multiple.copy(this.arguments)) {
            builder.append(" ");
            builder.append(arg);
        }
        builder.append(")");

        return builder.toString();
    }
}
