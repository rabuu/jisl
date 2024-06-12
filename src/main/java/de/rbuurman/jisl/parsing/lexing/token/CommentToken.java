package de.rbuurman.jisl.parsing.lexing.token;

public final class CommentToken extends StateToken<String> {
	public CommentToken(String comment) {
		super(comment);
	}
}
