package de.rbuurman.jisl.parsing.lexing.token;

public final class PrimitiveToken extends Token {
	private PrimitiveTokenType type;

	public PrimitiveToken(PrimitiveTokenType type) {
		this.type = type;
	}

	@Override
	public boolean exit() {
		return this.type == PrimitiveTokenType.EOF;
	}

	@Override
	public String toString() {
		switch (this.type) {
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
			case EOF:
				return "EOF";
			default:
				assert false;
				return "";
		}
	}
}
