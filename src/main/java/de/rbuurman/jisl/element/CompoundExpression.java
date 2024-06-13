package de.rbuurman.jisl.element;

import java.util.ArrayList;

public record CompoundExpression(Expression op, ArrayList<Expression> args) implements Expression {
}
