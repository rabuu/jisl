package de.rbuurman.jisl.element;

import java.util.ArrayList;

public record Lambda(ArrayList<Ident> idents, Expression expr) implements Value {
}
