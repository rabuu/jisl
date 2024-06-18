package de.rbuurman.jisl.lexing.matcher;

public interface Matcher {
	public boolean matches(char c);

	public default boolean matches(String s) {
		for (char c : s.toCharArray()) {
			if (!this.matches(c)) {
				return false;
			}
		}
		return true;
	}
}
