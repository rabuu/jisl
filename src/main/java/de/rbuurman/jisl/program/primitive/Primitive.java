package de.rbuurman.jisl.program.primitive;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public abstract class Primitive<T> implements Value {
    private T inner;

    public Primitive(T inner) {
        this.inner = inner;
    }

    public T getInner() {
        return this.inner;
    }

    public PrimitiveToken<Primitive<T>> toToken() {
        return new PrimitiveToken<Primitive<T>>(this);
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return this;
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
