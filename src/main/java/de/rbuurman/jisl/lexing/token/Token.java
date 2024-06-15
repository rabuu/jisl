package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;

/**
 * Token
 */
public abstract class Token {
	private SourcePosition sourcePosition;

	public boolean is(SimpleTokenType token) {
		return false;
	}

	public boolean exit() {
		return false;
	}

	public Token withPosition(SourcePosition sourcePosition) {
		this.sourcePosition = sourcePosition;
		return this;
	}

	public SourcePosition getSourcePosition() {
		return this.sourcePosition;
	}

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();
}
