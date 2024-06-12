package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.IdentToken;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public record Ident(String ident) implements Expression {
    public static Ident parse(Queue<Token> tokens) throws ParsingException {
        final var token = tokens.poll();
        if (token == null)
            throw ParsingException.EmptyTokenQueueException;

        if (token instanceof IdentToken) {
            return new Ident(((IdentToken) token).getState());
        }

        throw new ParsingException("Expected ident but got " + token);
    }
}
