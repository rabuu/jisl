package de.rbuurman.jisl.lexing.matcher;

public record InverseMatcher(Matcher matcher) implements Matcher {

	@Override
	public boolean matches(char c) {
		return !this.matcher.matches(c);
	}

}
