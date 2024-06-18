package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.utils.PeekableQueue;

public class TokenQueue extends PeekableQueue<Token<?>> {
	public boolean finished() {
		return this.size() == 0 || this.elements.peek().exit();
	}

	public boolean endOfExpression() {
		return this.finished() || this.elements.peek().is(SimpleTokenType.CLOSE);
	}

	public Token<?> expect(SimpleTokenType expected) throws ParsingException {
		final var token = this.poll();
		if (token == null)
			throw ParsingException.EmptyTokenQueueException;

		if (!token.is(expected)) {
			throw new ParsingException("Expected " + expected + " but got " + token, token.getSourcePosition());
		}

		return token;
	}
}
