package de.rbuurman.jisl.program.evaluation;

import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Applicables are Values that can transform S-expressions to other values.
 * You can think about them as functions.
 * <br>
 * The most important form of Applicable is the Lambda.
 * Also, there a a ton of builtin Applicables.
 */
public abstract class Applicable extends Value {

	public Applicable(SourcePosition sourcePosition) {
		super(sourcePosition);
	}

	public abstract Value lazy_apply(Multiple<Expression> arguments, Environment environment)
			throws EvaluationException;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		return getClass() == obj.getClass();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
