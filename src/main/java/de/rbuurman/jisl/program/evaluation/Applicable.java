package de.rbuurman.jisl.program.evaluation;

import java.util.Queue;

import de.rbuurman.jisl.program.Value;

/**
 * Applicable
 */
public interface Applicable {

	public Value apply(Queue<Value> arguments) throws EvaluationException;
}
