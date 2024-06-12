package de.rbuurman.jisl.elements;

import java.util.ArrayList;

public record CompoundExpression(Expression op, ArrayList<Expression> args) implements Expression {
}
