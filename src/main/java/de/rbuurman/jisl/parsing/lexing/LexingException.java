package de.rbuurman.jisl.parsing.lexing;

public class LexingException extends Exception {
	public LexingException(String err, SourcePosition pos) {
		super(err + " at " + pos);
	}
}
