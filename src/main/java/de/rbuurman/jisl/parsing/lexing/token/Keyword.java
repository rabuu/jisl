package de.rbuurman.jisl.parsing.lexing.token;

public enum Keyword {
	DEFINE;

	@Override
	public String toString() {
		switch (this) {
			case DEFINE:
				return "define";
			default:
				return null;
		}
	}
}
