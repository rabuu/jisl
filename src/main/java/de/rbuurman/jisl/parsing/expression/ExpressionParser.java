package de.rbuurman.jisl.parsing.expression;

import de.rbuurman.jisl.lexing.token.VariableNameToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.VariableNameParser;
import de.rbuurman.jisl.parsing.value.LambdaParser;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.value.ValueParser;
import de.rbuurman.jisl.program.expression.Expression;

/**
 * ExpressionParser
 */
public final class ExpressionParser extends Parser<Expression> {

    @Override
    public Expression parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peek().is(SimpleTokenType.OPEN)) {

            final Token<?> next = tokens.peekSecond();
            if (next.is(SimpleTokenType.LAMBDA)) {
                return new LambdaParser().parse(tokens);
            } else if (next.is(SimpleTokenType.COND)) {
                return new ConditionalExpressionParser().parse(tokens);
            } else if (next.is(SimpleTokenType.LOCAL)) {
                return new LocalExpressionParser().parse(tokens);
            }

            return new SExpressionParser().parse(tokens);
        }

        if (tokens.peek() instanceof VariableNameToken) {
            return new VariableNameParser().parse(tokens);
        }

        return new ValueParser().parse(tokens);
    }

}
