package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.IdentToken;
import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;
import de.rbuurman.jisl.parsing.value.LambdaValue;
import de.rbuurman.jisl.parsing.value.Value;

public interface Expression extends ProgramElement {
    public static Expression parseExpression(Queue<Token> tokens, final boolean removedOpen) throws ParsingException {
        final var firstToken = tokens.peek();
        if (removedOpen || firstToken.equals(SimpleTokenType.OPEN.toToken())) {
            if (!removedOpen) {
                tokens.remove();
            }

            if (tokens.peek().equals(SimpleTokenType.LAMBDA.toToken())) {
                return LambdaValue.parse(tokens);
            }
            return CompoundExpression.parse(tokens);
        } else if (firstToken instanceof IdentToken) {
            return Ident.parse(tokens);
        } else {
            return Value.parseValue(tokens);
        }
    }

    public static Expression parseExpression(Queue<Token> tokens) throws ParsingException {
        return Expression.parseExpression(tokens, false);
    }
}
