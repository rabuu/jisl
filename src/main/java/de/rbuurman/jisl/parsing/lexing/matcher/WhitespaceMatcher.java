package de.rbuurman.jisl.parsing.lexing.matcher;

public final class WhitespaceMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isWhitespace(c);
	}

}
