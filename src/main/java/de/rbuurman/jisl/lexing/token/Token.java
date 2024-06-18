package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;

/**
 * Token
 */
public abstract class Token<S> {
	private SourcePosition sourcePosition;
	private S state;

	protected Token(S state) {
		this.state = state;
	}

	public S getState() {
		return this.state;
	}

	public boolean is(SimpleTokenType token) {
		return false;
	}

	public boolean exit() {
		return false;
	}

	public Token<?> withPosition(SourcePosition sourcePosition) {
		this.sourcePosition = sourcePosition;
		return this;
	}

	public SourcePosition getSourcePosition() {
		return this.sourcePosition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		@SuppressWarnings("unchecked")
		Token<S> other = (Token<S>) obj;
		return this.state.equals(other.state);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.getState().toString();
	}
}
