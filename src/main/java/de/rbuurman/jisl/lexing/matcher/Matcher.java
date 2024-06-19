package de.rbuurman.jisl.lexing.matcher;

/**
 * A Matcher defines what characters are allowed in a specific context
 */
public interface Matcher {

	/**
	 * Returns whether `c` matches or not
	 * 
	 * @param c the character in question
	 */
	public boolean matches(char c);

	/**
	 * Returns whether a whole string matches
	 * <p>
	 * A string matches if every single character matches
	 *
	 * @param s the string in question
	 */
	public default boolean matches(String s) {
		for (char c : s.toCharArray()) {
			if (!this.matches(c)) {
				return false;
			}
		}
		return true;
	}
}
