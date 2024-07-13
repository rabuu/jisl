package de.rbuurman.jisl.program.evaluation;

import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.utils.SourcePosition;

/**
 * Applicable
 */
public abstract class ValueApplicable extends Applicable {

	public ValueApplicable(SourcePosition sourcePosition) {
		super(sourcePosition);
	}

	public abstract Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException;

	@Override
	public Value lazy_apply(Multiple<Expression> arguments, Environment environment) throws EvaluationException {
		var values = new Multiple<Value>();
		for (var arg : arguments) {
			values.add(arg.evaluate(environment));
		}

		return apply(values, environment);
	}

}
