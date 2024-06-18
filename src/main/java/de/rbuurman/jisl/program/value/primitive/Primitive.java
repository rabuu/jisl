package de.rbuurman.jisl.program.value.primitive;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class Primitive<T> extends Value {
    private T inner;

    public Primitive(T inner) {
        super(null);
        this.inner = inner;
    }

    public T getInner() {
        return this.inner;
    }

    public PrimitiveToken<Primitive<T>> toToken(SourcePosition sourcePosition) {
        return new PrimitiveToken<Primitive<T>>(this, sourcePosition);
    }

    @Override
    public String toString() {
        return this.inner.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        @SuppressWarnings("unchecked")
        Primitive<T> other = (Primitive<T>) obj;
        if (!this.inner.equals(other.inner))
            return false;

        return true;
    }
}
