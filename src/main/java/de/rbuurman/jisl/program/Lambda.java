package de.rbuurman.jisl.program;

import java.util.ArrayList;

public record Lambda(ArrayList<Identifier> identifiers, Expression expression) implements Value {
}
