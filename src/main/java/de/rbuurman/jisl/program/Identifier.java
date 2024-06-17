package de.rbuurman.jisl.program;

import java.util.Objects;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

public final class Identifier extends Expression {
    private String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return environment.getValue(this);
    }

    @Override
    public String toString() {
        return "`" + this.identifier + "`";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final Identifier other = (Identifier) obj;
        return this.identifier.equals(other.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }
}
