package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.Token;

public class ParsingUtils {
    public static void expectToken(Queue<Token> tokens, Token expected) throws ParsingException {
        final var token = tokens.poll();
        if (token == null)
            throw ParsingException.EmptyTokenQueueException;

        if (!token.equals(expected)) {
            throw new ParsingException("Expected " + expected + " but got " + token);
        }
    }
}
