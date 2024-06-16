package de.rbuurman.jisl.lexing.matcher;

public record OrMatcher(Matcher matcher1, Matcher matcher2) implements Matcher {

	@Override
	public boolean matches(char c) {
		return this.matcher1.matches(c) || this.matcher2.matches(c);
	}

}
