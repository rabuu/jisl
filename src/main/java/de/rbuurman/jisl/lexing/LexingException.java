package de.rbuurman.jisl.lexing;

import de.rbuurman.jisl.utils.SourcePosition;

/**
 * An Exception that originates from a lexical incorrect source file
 */
public class LexingException extends Exception {
	public LexingException(String err, SourcePosition pos) {
		super(err + " at " + pos);
	}
}
