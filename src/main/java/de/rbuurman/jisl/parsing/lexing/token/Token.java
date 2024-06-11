package de.rbuurman.jisl.parsing.lexing.token;

public abstract class Token {
	@Override
	public abstract String toString();

	@Override
	public abstract boolean equals(Object obj);

	public boolean exit() {
		return false;
	}
}
