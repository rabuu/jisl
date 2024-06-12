package de.rbuurman.jisl.elements.value;

import java.util.ArrayList;

import de.rbuurman.jisl.elements.*;

public record LambdaValue(ArrayList<Ident> idents, Expression expr) implements Value {
}
