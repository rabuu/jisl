package de.rbuurman.jisl.lexing.matcher;

/**
 * A matcher that matches a whole line
 */
public final class LineMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return !(c == '\n' || c == '\r');
	}

}
