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
	public String toString() {
		if (this.getSourcePosition() == null) {
			return this.getClass().getSimpleName() + ": " + this.getState();
		}
		return this.getClass().getSimpleName() + ": " + this.getState() + " " + this.getSourcePosition();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		StateToken<S> other = (StateToken<S>) obj;
		if (!this.state.equals(other.state)) {
			return false;
		}

		return true;
	}
}
