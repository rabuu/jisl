package de.rbuurman.jisl.program.expression;

import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.StructDefinition;
import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * LocalExpression
 */
public final class LocalExpression extends Expression {
    private final Multiple<Definition> definitions;
    private final Multiple<StructDefinition> structs;
    private Expression expression;

    public LocalExpression(Multiple<Definition> definitions, Multiple<StructDefinition> structs, Expression expression,
            SourcePosition sourcePosition) {
        super(sourcePosition);
        this.definitions = definitions;
        this.structs = structs;
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
        for (StructDefinition struct : this.structs) {
            final var name = struct.getName();
            final var fields = struct.getFields();

            localEnvironment.addStruct(name, fields);
            mergedEnvironment = Environment.merge(environment, localEnvironment);
        }

        return this.expression.evaluate(mergedEnvironment);
    }

    @Override
    public Expression replace(VariableName variable, Value value) {
        for (var def : this.definitions) {
            if (def.getVariable().equals(variable)) {
                return this;
            }
        }

        // TODO: structs shadowing

        var newExpr = this.expression.replace(variable, value);
        return new LocalExpression(definitions, structs, newExpr, this.getSourcePosition());
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
        return "(local (" + this.definitions + this.structs + ") " + this.expression + ")";
    }

}
