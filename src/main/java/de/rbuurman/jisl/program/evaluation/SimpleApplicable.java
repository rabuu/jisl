package de.rbuurman.jisl.program.evaluation;

/**
 * SimpleApplicable
 */
public abstract class SimpleApplicable extends Applicable {

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
