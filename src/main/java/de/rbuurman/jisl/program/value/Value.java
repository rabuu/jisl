package de.rbuurman.jisl.program.value;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * A Value is the end product of the evaluation.
 * This is what eventually gets printed to screen if we run a program.
 *
 * There are a few different Value types:
 * - Primtives like Numbers, String, Booleans, ...
 * - List data
 * - Lambdas/functions
 * - Instances of structs
 */
public abstract class Value extends Expression {

    public Value(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    @Override
    public Expression replace(VariableName variable, Value value) {
        return this;
    }

    @Override
    public Value evaluate(Environment environment) throws EvaluationException {
        return this;
    }

    @Override
    public abstract boolean equals(Object obj);

}
