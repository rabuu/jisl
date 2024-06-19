package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;

/**
 * A Token is a part of a source text that has an attached meaning
 * <p>
 * Tokens are an important abstraction because we don't want to work with
 * strings but with logical separated chunks of syntax.
 * <p>
 * Examples for sensible Tokens would be:
 * - simple syntax elements such as opening and closing parenthesis
 * - primtives such as strings, numbers, booleans, ...
 * - a keyword such as define, lambda, ...
 */
public abstract class Token<S> {
	// Each Token stores where it was located in the source file
	private SourcePosition sourcePosition;

	// Each Token has a state attached to enclose a piece of data
	private S state;

	protected Token(S state, SourcePosition sourcePosition) {
		this.state = state;
		this.sourcePosition = sourcePosition;
	}

	protected S getState() {
		return this.state;
	}

	public SourcePosition getSourcePosition() {
		return this.sourcePosition;
	}

	/**
	 * Returns whether the Token matches a SimpleTokenType
	 * <p>
	 * By default a Token does not because it only makes sense for SimpleTokens
	 * to match a SimpleTokenType.
	 */
	public boolean is(SimpleTokenType token) {
		return false;
	}

	/**
	 * Return whether parsing should stop when encountering the Token
	 * <p>
	 * It is basically an overcomplicated way to check for SimpleTokenType.EOF.
	 */
	public boolean exit() {
		return false;
	}

	// A Token equals another when the state matches
	// Note that the source position doesn't matter for equality
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
