package de.rbuurman.jisl.parsing.lexing.token;

public abstract class StateToken<T> extends Token {
	private T state;

	public StateToken(T state) {
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
		return this.getClass().getSimpleName() + ": " + this.getState();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StateToken) {
			StateToken<T> tok = (StateToken<T>) obj;
			return this.compareState(tok.state);
		}

		return false;
	}
}
