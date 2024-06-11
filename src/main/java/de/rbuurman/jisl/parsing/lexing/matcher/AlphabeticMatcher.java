package de.rbuurman.jisl.parsing.lexing.matcher;

public final class AlphabeticMatcher extends Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isAlphabetic(c);
	}

}
