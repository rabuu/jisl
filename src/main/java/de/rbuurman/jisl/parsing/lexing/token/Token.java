package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public abstract class Token {
	private SourcePosition sourcePosition;

	public Token(SourcePosition sourcePosition) {
		this.sourcePosition = sourcePosition;
	}

	@Override
	public abstract String toString();

	@Override
	public abstract boolean equals(Object obj);

	public boolean exit() {
		return false;
	}

	public SourcePosition getSourcePosition() {
		return sourcePosition;
	}
}
