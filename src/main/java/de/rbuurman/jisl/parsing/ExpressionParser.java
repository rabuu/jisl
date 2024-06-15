package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.IdentifierToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Expression;

/**
 * ExpressionParser
 */
public final class ExpressionParser extends Parser<Expression> {

    @Override
    public Expression parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peek().is(SimpleTokenType.OPEN) && !tokens.peekNth(2).is(SimpleTokenType.LAMBDA)) {
            return new SExpressionParser().parse(tokens);
        }

        if (tokens.peek() instanceof IdentifierToken) {
            return new IdentifierParser().parse(tokens);
        }

        return new ValueParser().parse(tokens);
    }

}
