package de.rbuurman.jisl.parsing;

import java.util.ArrayList;
import java.util.Queue;

import de.rbuurman.jisl.elements.*;
import de.rbuurman.jisl.elements.value.*;
import de.rbuurman.jisl.parsing.lexing.token.*;
import de.rbuurman.jisl.parsing.lexing.token.SimpleToken.Type;

public class ProgramElementParser {
    public static ProgramElement parseProgramElement(Queue<Token> tokens) throws ParsingException {
        boolean removedOpen = false;
        if (tokens.peek().equals(new SimpleToken(Type.OPEN))) {
            tokens.remove();
            removedOpen = true;
        }

        boolean definition = false;
        if (tokens.peek().equals(new SimpleToken(Type.DEFINE)))
            definition = true;

        if (definition) {
            return parseDefinition(tokens);
        } else {
            return parseExpression(tokens, removedOpen);
        }
    }

    public static Definition parseDefinition(Queue<Token> tokens) throws ParsingException {
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.DEFINE));
        final var ident = parseIdent(tokens);
        final var expr = parseExpression(tokens);
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.CLOSE));

        return new Definition(ident, expr);
    }

    public static Expression parseExpression(Queue<Token> tokens, final boolean removedOpen) throws ParsingException {
        final var firstToken = tokens.peek();
        if (removedOpen || firstToken.equals(new SimpleToken(Type.OPEN))) {
            if (!removedOpen) {
                tokens.remove();
            }

            if (tokens.peek().equals(new SimpleToken(Type.LAMBDA))) {
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
        while (!tokens.peek().equals(new SimpleToken(Type.CLOSE)) && !tokens.peek().exit()) {
            args.add(parseExpression(tokens));
        }

        if (args.size() == 0) {
            throw new ParsingException("Empty expression");
        }

        ParsingUtils.expectToken(tokens, new SimpleToken(Type.CLOSE));

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

        if (firstToken.equals(new SimpleToken(Type.OPEN))) {
            return null;
        }

        if (firstToken instanceof ValueToken) {
            return ((ValueToken) firstToken).toValue();
        }

        throw new ParsingException("Couldn't parse " + firstToken + " to value");
    }

    public static LambdaValue parseLambdaValue(Queue<Token> tokens) throws ParsingException {
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.LAMBDA));
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.OPEN));

        var idents = new ArrayList<Ident>();
        while (!tokens.peek().equals(new SimpleToken(Type.CLOSE)) && !tokens.peek().exit()) {
            idents.add(parseIdent(tokens));
        }
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.CLOSE));

        if (idents.size() == 0) {
            throw new ParsingException("Lambda expression invalid without idents");
        }

        final var expr = parseExpression(tokens);
        ParsingUtils.expectToken(tokens, new SimpleToken(Type.CLOSE));

        return new LambdaValue(idents, expr);
    }
}
