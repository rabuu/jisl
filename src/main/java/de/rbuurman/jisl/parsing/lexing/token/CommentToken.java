package de.rbuurman.jisl.parsing.lexing.token;

public final class CommentToken extends Token {
	private String comment;

	public CommentToken(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "Comment: " + this.comment;
	}
}
