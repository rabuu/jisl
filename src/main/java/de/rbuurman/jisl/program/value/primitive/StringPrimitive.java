package de.rbuurman.jisl.program.value.primitive;

public final class StringPrimitive extends Primitive<String> {
    public StringPrimitive(String inner) {
        super(inner);
    }

    @Override
    public String toString() {
        return "\"" + this.getInner().toString() + "\"";
    }
}
