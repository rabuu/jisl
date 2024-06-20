package de.rbuurman.jisl.program.value.primitive;

public final class SymbolPrimitive extends Primitive<String> {
    public SymbolPrimitive(String inner) {
        super(inner);
    }

    @Override
    public String toString() {
        return "'" + this.getInner();
    }
}
