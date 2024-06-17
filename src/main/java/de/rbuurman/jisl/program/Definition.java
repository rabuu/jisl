package de.rbuurman.jisl.program;

public final class Definition extends ProgramElement {
    private Identifier identifier;
    private Expression expression;

    public Definition(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "DEFINITION: " + identifier + " -> " + this.expression;
    }

}
