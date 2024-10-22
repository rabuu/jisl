package de.rbuurman.jisl.program.value.primitive;

public final class BooleanPrimitive extends Primitive<Boolean> {
    public BooleanPrimitive(Boolean inner) {
        super(inner);
    }

    @Override
    public String toString() {
        return "#" + this.getInner().toString();
    }
}
