package de.rbuurman.jisl.parsing.lexing.matcher;

public final class LineMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return !(c == '\n' || c == '\r');
	}

}
