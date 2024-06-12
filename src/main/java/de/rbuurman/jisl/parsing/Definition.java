package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public record Definition(Ident ident, Expression expr) implements ProgramElement {
    public static Definition parse(Queue<Token> tokens) throws ParsingException {
        ParsingUtils.expectToken(tokens, SimpleTokenType.DEFINE.toToken());
        final var ident = Ident.parse(tokens);
        final var expr = Expression.parse(tokens);
        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        return new Definition(ident, expr);
    }
}
