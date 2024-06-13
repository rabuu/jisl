package de.rbuurman.jisl.element;

import java.util.ArrayList;

public record SExpression(Expression op, ArrayList<Expression> args) implements Expression {
}
