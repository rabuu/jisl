package de.rbuurman.jisl.program;

import java.util.ArrayList;

public record SExpression(ArrayList<Expression> expressions) implements Expression {
}
