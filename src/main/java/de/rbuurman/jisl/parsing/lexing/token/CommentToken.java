package de.rbuurman.jisl.parsing.lexing.token;

public final class CommentToken implements Token {
	private String comment;

	public CommentToken(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
}
