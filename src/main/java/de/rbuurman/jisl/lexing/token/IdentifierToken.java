package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.expression.Identifier;

public final class IdentifierToken extends Token<String> {
	public IdentifierToken(String identifier) {
		super(identifier);
	}

	public Identifier toIdentifier() {
		return new Identifier(this.getState());
	}
}
