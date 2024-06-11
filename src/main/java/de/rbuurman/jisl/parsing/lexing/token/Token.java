package de.rbuurman.jisl.parsing.lexing.token;

public interface Token {
	default boolean exit() {
		return false;
	}
}
