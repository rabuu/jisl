package de.rbuurman.jisl.program;

import java.util.Queue;

public record Lambda(Queue<Identifier> identifiers, Expression expression) implements Value {
}
