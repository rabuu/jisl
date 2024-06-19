package de.rbuurman.jisl.lexing.matcher;

/**
 * A Matcher that matches everything that is allowed in word
 * such as a variable name
 */
public final class WordMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		if (Character.isWhitespace(c))
			return false;
		final char[] forbidden = { '"', '\'', '`', '(', ')', '[', ']', '{', '}', '|', ';', '#' };
		for (char f : forbidden) {
			if (c == f)
				return false;
		}
		return true;
	}

}
