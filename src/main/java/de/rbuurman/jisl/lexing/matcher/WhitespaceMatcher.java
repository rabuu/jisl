package de.rbuurman.jisl.lexing.matcher;

public final class WhitespaceMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isWhitespace(c);
	}

}
