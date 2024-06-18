package de.rbuurman.jisl.program.expression;

import java.util.Objects;

import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.SourcePosition;

public final class VariableName extends Expression {
    private String name;

    public VariableName(String name, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.name = name;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return environment.getValue(this);
    }

    @Override
    public String toString() {
        return "`" + this.name + "`";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final VariableName other = (VariableName) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
