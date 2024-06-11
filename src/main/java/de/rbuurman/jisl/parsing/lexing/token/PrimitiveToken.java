package de.rbuurman.jisl.parsing.lexing.token;

public enum PrimitiveToken implements Token {
	PAREN_OPEN,
	PAREN_CLOSE,
	BRACKET_OPEN,
	BRACKET_CLOSE,

	PLUS,
	MINUS,

	EOF;

	@Override
	public boolean exit() {
		return this == PrimitiveToken.EOF;
	}
}
