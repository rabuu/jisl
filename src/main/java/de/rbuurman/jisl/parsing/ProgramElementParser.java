package de.rbuurman.jisl.parsing;

import java.util.ArrayList;
import java.util.Queue;

import de.rbuurman.jisl.element.*;
import de.rbuurman.jisl.lexing.token.IdentToken;
import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.Type;

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

            // FIXME: lambdas are values!
            if (tokens.peek().equals(new SimpleToken(Type.LAMBDA))) {
                return parseLambda(tokens);
            }
            return parseSExpression(tokens);
        } else if (firstToken instanceof IdentToken) {
            return parseIdent(tokens);
        } else {
            return parseValue(tokens);
        }
    }

    public static Expression parseExpression(Queue<Token> tokens) throws ParsingException {
        return parseExpression(tokens, false);
    }

    public static SExpression parseSExpression(Queue<Token> tokens) throws ParsingException {
        final var op = parseExpression(tokens);

        var args = new ArrayList<Expression>();
        while (!tokens.peek().equals(new SimpleToken(Type.CLOSE)) && !tokens.peek().exit()) {
            args.add(parseExpression(tokens));
        }

        if (args.size() == 0) {
            throw new ParsingException("Empty expression");
        }

        ParsingUtils.expectToken(tokens, new SimpleToken(Type.CLOSE));

        return new SExpression(op, args);
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

        if (firstToken instanceof PrimitiveToken<?>) {
            return ((PrimitiveToken<?>) firstToken).getState();
        }

        throw new ParsingException("Couldn't parse " + firstToken + " to value");
    }

    public static Lambda parseLambda(Queue<Token> tokens) throws ParsingException {
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

        return new Lambda(idents, expr);
    }
}
