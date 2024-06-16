package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.IdentifierToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Expression;

/**
 * ExpressionParser
 */
public final class ExpressionParser extends Parser<Expression> {

    @Override
    public Expression parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peek().is(SimpleTokenType.OPEN)) {

            final Token<?> next = tokens.peekNth(2);
            if (next.is(SimpleTokenType.LAMBDA)) {
                return new LambdaParser().parse(tokens);
            } else if (next.is(SimpleTokenType.COND)) {
                return new ConditionalParser().parse(tokens);
            }

            return new SExpressionParser().parse(tokens);
        }

        if (tokens.peek() instanceof IdentifierToken) {
            return new IdentifierParser().parse(tokens);
        }

        return new ValueParser().parse(tokens);
    }

}
