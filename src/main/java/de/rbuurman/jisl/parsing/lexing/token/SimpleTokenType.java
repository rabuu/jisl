package de.rbuurman.jisl.parsing.lexing.token;

public enum SimpleTokenType {
	PAREN_OPEN,
	PAREN_CLOSE,
	BRACKET_OPEN,
	BRACKET_CLOSE,

	PLUS,
	MINUS,

	DEFINE,

	EOF;

	@Override
	public String toString() {
		switch (this) {
			case PAREN_OPEN:
				return "(";
			case PAREN_CLOSE:
				return ")";
			case BRACKET_OPEN:
				return "[";
			case BRACKET_CLOSE:
				return "]";
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case DEFINE:
				return "define";
			case EOF:
				return "EOF";
			default:
				return null;
		}
	}
}
