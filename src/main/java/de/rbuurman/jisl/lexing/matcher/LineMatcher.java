package de.rbuurman.jisl.lexing.matcher;

public final class LineMatcher implements Matcher {

	@Override
	public boolean matches(char c) {
		return !(c == '\n' || c == '\r');
	}

}
