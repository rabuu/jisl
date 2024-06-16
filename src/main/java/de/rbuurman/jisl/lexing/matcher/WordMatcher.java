package de.rbuurman.jisl.lexing.matcher;

public final class WordMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isAlphabetic(c);
	}

}
