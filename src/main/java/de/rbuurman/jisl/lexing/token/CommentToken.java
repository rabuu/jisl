package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.utils.SourcePosition;

public final class CommentToken extends Token<String> {
	public CommentToken(String comment, SourcePosition sourcePosition) {
		super(comment, sourcePosition);
	}
}
