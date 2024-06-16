package de.rbuurman.jisl.lexing.matcher;

public final class IdentifierMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		final char[] forbidden = { ' ', '"', '\'', '`', '(', ')', '[', ']', '{', '}', '|', ';', '#' };
		for (char f : forbidden) {
			if (c == f)
				return false;
		}
		return true;
	}

}
