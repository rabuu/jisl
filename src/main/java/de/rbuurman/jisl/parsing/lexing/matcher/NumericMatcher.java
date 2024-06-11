package de.rbuurman.jisl.parsing.lexing.matcher;

public final class NumericMatcher extends Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isDigit(c) || c == '.';
	}

}
