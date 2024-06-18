package de.rbuurman.jisl.program.evaluation;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * EvaluationException
 */
public final class EvaluationException extends Exception {
	public EvaluationException(String err, SourcePosition sourcePosition) {
		super(err + " at " + sourcePosition);
	}
}
