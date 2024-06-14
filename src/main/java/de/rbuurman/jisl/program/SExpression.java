package de.rbuurman.jisl.program;

import java.util.ArrayList;

public record SExpression(Expression op, ArrayList<Expression> args) implements Expression {
}
