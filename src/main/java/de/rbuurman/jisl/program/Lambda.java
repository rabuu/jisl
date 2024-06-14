package de.rbuurman.jisl.program;

import java.util.ArrayList;

public record Lambda(ArrayList<Ident> idents, Expression expr) implements Value {
}
