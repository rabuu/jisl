package de.rbuurman.jisl.lexing.matcher;

public final class InverseMatcher implements Matcher {
	private Matcher matcher;

	public InverseMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matches(char c) {
		return !this.matcher.matches(c);
	}

}
