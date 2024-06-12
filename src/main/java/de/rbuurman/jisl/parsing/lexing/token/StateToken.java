package de.rbuurman.jisl.parsing.lexing.token;

import java.lang.reflect.ParameterizedType;

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
		Class<T> stateType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		if (obj instanceof StateToken) {
			StateToken<T> tok = (StateToken<T>) obj;
			return this.compareState(tok.state);
		} else if (stateType.isInstance(obj)) {
			return this.compareState(stateType.cast(obj));
		}

		return false;
	}
}
