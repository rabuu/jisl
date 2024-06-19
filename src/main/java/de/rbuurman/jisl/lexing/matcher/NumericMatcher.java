package de.rbuurman.jisl.lexing.matcher;

/**
 * A Matcher that matches character a number literal may contain
 */
public final class NumericMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isDigit(c) || c == '-' || c == '.' || c == '/';
	}

}
