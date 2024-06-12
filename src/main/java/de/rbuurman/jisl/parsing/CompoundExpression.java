package de.rbuurman.jisl.parsing;

public record CompoundExpression(Expression op, Expression[] args) implements Expression {
}
