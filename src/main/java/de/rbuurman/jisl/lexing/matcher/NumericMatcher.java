package de.rbuurman.jisl.lexing.matcher;

public final class NumericMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isDigit(c) || c == '-' || c == '.' || c == '/';
	}

}
