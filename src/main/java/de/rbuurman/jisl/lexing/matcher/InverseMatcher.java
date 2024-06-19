package de.rbuurman.jisl.lexing.matcher;

/**
 * A matcher that matches when another *doesn't* match
 */
public record InverseMatcher(Matcher matcher) implements Matcher {

	@Override
	public boolean matches(char c) {
		return !this.matcher.matches(c);
	}

}
