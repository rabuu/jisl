package de.rbuurman.jisl.program.expression;

import de.rbuurman.jisl.program.ProgramElement;
import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

/**
 * An Expression is what you give your Program to evaluate.
 * This is why an Expression is basically defined by its property that it can
 * evaluate() to a Value.
 * <br>
 * There are a few different types of Expressions:
 * - Values itself are Expressions
 * - S-expressions are of the form (applicable expr ...)
 * - There is special conditional expression (cond [...] ... [else ...])
 * - Local expressions that define their own environment
 * (local [definition...] expr)
 */
public abstract class Expression extends ProgramElement {

    public Expression(SourcePosition sourcePosition) {
        super(sourcePosition);
    }

    public abstract Value evaluate(Environment environment) throws EvaluationException;

    public abstract Expression replace(VariableName variable, Value value);

    public Expression replace(Environment replacements) {
        var expr = this;
        for (var definition : replacements.getDefinitions().entrySet()) {
            expr = expr.replace(definition.getKey(), definition.getValue());
        }
        return expr;
    }

    @Override
    public abstract boolean equals(Object obj);
}
