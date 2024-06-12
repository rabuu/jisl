package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public abstract class StateToken<T> extends Token {
	private T state;

	public StateToken(T state, SourcePosition sourcePosition) {
		super(sourcePosition);
		this.state = state;
	}

	public T getState() {
		return state;
	}

	public boolean compareState(T cmp) {
		return this.state == cmp;
	}

	@Override
	public String toString() {
		if (this.getSourcePosition() == null) {
			return this.getClass().getSimpleName() + ": " + this.getState();
		}
		return this.getClass().getSimpleName() + ": " + this.getState() + " " + this.getSourcePosition();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StateToken<?>) {
			StateToken<?> tok = (StateToken<?>) obj;
			try {
				return this.compareState((T) tok.getState());
			} catch (ClassCastException e) {
				return false;
			}
		}

		return false;
	}
}
