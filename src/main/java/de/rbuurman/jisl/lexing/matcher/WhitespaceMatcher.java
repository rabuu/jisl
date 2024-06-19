package de.rbuurman.jisl.lexing.matcher;

/**
 * A matcher that only matches whitespaces
 */
public final class WhitespaceMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isWhitespace(c);
	}

}
