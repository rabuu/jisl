package de.rbuurman.jisl.parsing.expression;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.expression.SExpression;
import de.rbuurman.jisl.utils.Multiple;

/**
 * SExpressionParser
 */
public final class SExpressionParser extends Parser<SExpression> {

    @Override
    public SExpression parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);

        var func = new ExpressionParser().parse(tokens);

        Multiple<Expression> args = new Multiple<>();
        while (!tokens.endOfExpression()) {
            args.add(new ExpressionParser().parse(tokens));
        }

        if (args.size() < 1) {
            throw new ParsingException("S-expression must have at least one argument", open.getSourcePosition());
        }

        tokens.expect(SimpleTokenType.CLOSE);

        return new SExpression(func, args);
    }

}