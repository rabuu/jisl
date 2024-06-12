package de.rbuurman.jisl.parsing;

import java.util.ArrayList;
import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public record CompoundExpression(Expression op, ArrayList<Expression> args) implements Expression {
    public static CompoundExpression parse(Queue<Token> tokens, boolean removedOpen) throws ParsingException {
        if (!removedOpen)
            ParsingUtils.expectToken(tokens, SimpleTokenType.OPEN.toToken());

        final var op = Expression.parseExpression(tokens);

        var args = new ArrayList<Expression>();
        while (!tokens.peek().equals(SimpleTokenType.CLOSE.toToken()) && !tokens.peek().exit()) {
            args.add(Expression.parseExpression(tokens));
        }

        if (args.size() == 0) {
            throw new ParsingException("Empty expression");
        }

        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        return new CompoundExpression(op, args);
    }

    public static CompoundExpression parse(Queue<Token> tokens) throws ParsingException {
        return CompoundExpression.parse(tokens, false);
    }
}
