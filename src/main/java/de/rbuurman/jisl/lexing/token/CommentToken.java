package de.rbuurman.jisl.lexing.token;

public final class CommentToken extends StateToken<String> {
	public CommentToken(String comment) {
		super(comment);
	}
}
