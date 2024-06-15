package de.rbuurman.jisl.lexing;

import de.rbuurman.jisl.utils.SourcePosition;

public class LexingException extends Exception {
	public LexingException(String err, SourcePosition pos) {
		super(err + " at " + pos);
	}
}
