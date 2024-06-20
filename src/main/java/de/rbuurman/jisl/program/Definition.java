package de.rbuurman.jisl.program;

import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.SourcePosition;

public final class Definition extends ProgramElement {
    private VariableName variable;
    private Expression expression;

    public Definition(VariableName variable, Expression expression, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.variable = variable;
        this.expression = expression;
    }

    public VariableName getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "DEFINITION: " + variable + " -> " + this.expression;
    }

}
