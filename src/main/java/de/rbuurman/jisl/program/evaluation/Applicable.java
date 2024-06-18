package de.rbuurman.jisl.program.evaluation;

import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Applicable
 */
public abstract class Applicable extends Value {

	public abstract Value apply(Multiple<Value> arguments, Environment environment) throws EvaluationException;

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
