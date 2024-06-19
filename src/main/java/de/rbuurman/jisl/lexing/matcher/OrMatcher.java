package de.rbuurman.jisl.lexing.matcher;

/**
 * A Matcher that matches when one specific matcher *or* another
 * specific matcher matches
 */
public record OrMatcher(Matcher matcher1, Matcher matcher2) implements Matcher {

	@Override
	public boolean matches(char c) {
		return this.matcher1.matches(c) || this.matcher2.matches(c);
	}

}
