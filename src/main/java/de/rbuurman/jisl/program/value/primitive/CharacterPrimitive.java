package de.rbuurman.jisl.program.value.primitive;

public final class CharacterPrimitive extends Primitive<Character> {
    public CharacterPrimitive(Character inner) {
        super(inner);
    }

    @Override
    public String toString() {
        return "#\\" + this.getInner();
    }
}
