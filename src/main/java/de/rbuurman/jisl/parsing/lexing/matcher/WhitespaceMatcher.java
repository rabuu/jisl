package de.rbuurman.jisl.parsing.lexing.matcher;

public final class WhitespaceMatcher extends Matcher {

	@Override
	public boolean matches(char c) {
		return Character.isWhitespace(c);
	}

}
