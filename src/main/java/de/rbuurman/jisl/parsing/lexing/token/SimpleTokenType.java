package de.rbuurman.jisl.parsing.lexing.token;

public enum SimpleTokenType {
	OPEN,
	CLOSE,

	PLUS,
	MINUS,

	DEFINE,
	LAMBDA,

	EOF;

	public SimpleToken toToken() {
		return new SimpleToken(this, null);
	}

	@Override
	public String toString() {
		switch (this) {
			case OPEN:
				return "(";
			case CLOSE:
				return ")";
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case DEFINE:
				return "define";
			case LAMBDA:
				return "lambda";
			case EOF:
				return "EOF";
			default:
				return null;
		}
	}
}
