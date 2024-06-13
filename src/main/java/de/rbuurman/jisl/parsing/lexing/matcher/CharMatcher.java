package de.rbuurman.jisl.parsing.lexing.matcher;

public final class CharMatcher implements Matcher {
	private char character;

	public CharMatcher(char character) {
		this.character = character;
	}

	@Override
	public boolean matches(char c) {
		return this.character == c;
	}

}
