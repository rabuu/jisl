package de.rbuurman.jisl.lexing.token;

public abstract class StateToken<S> extends Token {
	private S state;

	public StateToken(S state) {
		this.state = state;
	}

	public S getState() {
		return state;
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
		StateToken<S> other = (StateToken<S>) obj;
		return this.state.equals(other.state);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.getState().toString();
	}
}
