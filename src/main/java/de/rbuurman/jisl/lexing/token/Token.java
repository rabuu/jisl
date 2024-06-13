package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.lexing.SourcePosition;

public abstract class Token {
	private SourcePosition sourcePosition;

	public Token withSourcePosition(SourcePosition sourcePosition) {
		this.sourcePosition = sourcePosition;
		return this;
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
