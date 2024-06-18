package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;

public final class VariableNameToken extends Token<String> {
	public VariableNameToken(String name, SourcePosition sourcePosition) {
		super(name, sourcePosition);
	}

	public String toInner() {
		return this.getState();
	}
}
