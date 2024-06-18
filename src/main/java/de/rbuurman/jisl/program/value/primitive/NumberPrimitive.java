package de.rbuurman.jisl.program.value.primitive;

/**
 * NumberPrimitive
 */
public class NumberPrimitive extends Primitive<Double> {
    public NumberPrimitive(Double inner) {
        super(inner);
    }

    @Override
    public String toString() {
        if (this.getInner() == this.getInner().intValue()) {
            return "" + this.getInner().intValue();
        } else {
            return this.getInner().toString();
        }
    }
}
