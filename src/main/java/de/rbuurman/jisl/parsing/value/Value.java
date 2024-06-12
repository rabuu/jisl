package de.rbuurman.jisl.parsing.value;

import java.util.Queue;

import de.rbuurman.jisl.parsing.Expression;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;
import de.rbuurman.jisl.parsing.lexing.token.ValueToken;

public interface Value extends Expression {
	public static Value parseValue(Queue<Token> tokens) throws ParsingException {
		final var firstToken = tokens.poll();
		if (firstToken == null)
			throw ParsingException.EmptyTokenQueueException;

		if (firstToken.equals(SimpleTokenType.OPEN.toToken())) {
			return null;
		}

		if (firstToken instanceof ValueToken) {
			return ((ValueToken) firstToken).toValue();
		}

		throw new ParsingException("Couldn't parse " + firstToken + " to value");
	}
}
