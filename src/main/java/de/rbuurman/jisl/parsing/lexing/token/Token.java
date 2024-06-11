package de.rbuurman.jisl.parsing.lexing.token;

public abstract class Token {
	@Override
	public abstract String toString();

	public boolean exit() {
		return false;
	}
}
