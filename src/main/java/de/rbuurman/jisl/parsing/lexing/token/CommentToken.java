package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;

public final class CommentToken extends StateToken<String> {
	public CommentToken(String comment, SourcePosition sourcePosition) {
		super(comment, sourcePosition);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}
}
