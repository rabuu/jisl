package de.rbuurman.jisl.parsing;

import java.util.ArrayList;
import java.util.Queue;

import de.rbuurman.jisl.elements.*;
import de.rbuurman.jisl.elements.value.*;
import de.rbuurman.jisl.parsing.lexing.token.*;

public class ProgramElementParser {
    public static ProgramElement parseProgramElement(Queue<Token> tokens) throws ParsingException {
        boolean removedOpen = false;
        if (tokens.peek().equals(SimpleTokenType.OPEN.toToken())) {
            tokens.remove();
            removedOpen = true;
        }

        boolean definition = false;
        if (tokens.peek().equals(SimpleTokenType.DEFINE.toToken()))
            definition = true;

        if (definition) {
            return parseDefinition(tokens);
        } else {
            return parseExpression(tokens, removedOpen);
        }
    }

    public static Definition parseDefinition(Queue<Token> tokens) throws ParsingException {
        ParsingUtils.expectToken(tokens, SimpleTokenType.DEFINE.toToken());
        final var ident = parseIdent(tokens);
        final var expr = parseExpression(tokens);
        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        return new Definition(ident, expr);
    }

    public static Expression parseExpression(Queue<Token> tokens, final boolean removedOpen) throws ParsingException {
        final var firstToken = tokens.peek();
        if (removedOpen || firstToken.equals(SimpleTokenType.OPEN.toToken())) {
            if (!removedOpen) {
                tokens.remove();
            }

            if (tokens.peek().equals(SimpleTokenType.LAMBDA.toToken())) {
                return parseLambdaValue(tokens);
            }
            return parseCompoundExpression(tokens);
        } else if (firstToken instanceof IdentToken) {
            return parseIdent(tokens);
        } else {
            return parseValue(tokens);
        }
    }

    public static Expression parseExpression(Queue<Token> tokens) throws ParsingException {
        return parseExpression(tokens, false);
    }

    public static CompoundExpression parseCompoundExpression(Queue<Token> tokens) throws ParsingException {
        final var op = parseExpression(tokens);

        var args = new ArrayList<Expression>();
        while (!tokens.peek().equals(SimpleTokenType.CLOSE.toToken()) && !tokens.peek().exit()) {
            args.add(parseExpression(tokens));
        }

        if (args.size() == 0) {
            throw new ParsingException("Empty expression");
        }

        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        return new CompoundExpression(op, args);
    }

    public static Ident parseIdent(Queue<Token> tokens) throws ParsingException {
        final var token = tokens.poll();
        if (token == null)
            throw ParsingException.EmptyTokenQueueException;

        if (token instanceof IdentToken) {
            return new Ident(((IdentToken) token).getState());
        }

        throw new ParsingException("Expected ident but got " + token);
    }

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

    public static LambdaValue parseLambdaValue(Queue<Token> tokens) throws ParsingException {
        ParsingUtils.expectToken(tokens, SimpleTokenType.LAMBDA.toToken());
        ParsingUtils.expectToken(tokens, SimpleTokenType.OPEN.toToken());

        var idents = new ArrayList<Ident>();
        while (!tokens.peek().equals(SimpleTokenType.CLOSE.toToken()) && !tokens.peek().exit()) {
            idents.add(parseIdent(tokens));
        }
        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        if (idents.size() == 0) {
            throw new ParsingException("Lambda expression invalid without idents");
        }

        final var expr = parseExpression(tokens);
        ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

        return new LambdaValue(idents, expr);
    }
}
