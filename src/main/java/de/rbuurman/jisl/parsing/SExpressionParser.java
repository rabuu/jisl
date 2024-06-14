package de.rbuurman.jisl.parsing;

import java.util.ArrayList;

import de.rbuurman.jisl.lexing.token.SimpleToken.Type;
import de.rbuurman.jisl.program.Expression;
import de.rbuurman.jisl.program.SExpression;

/**
 * SExpressionParser
 */
public final class SExpressionParser extends Parser<SExpression> {

    @Override
    public SExpression parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(Type.OPEN);

        var exprs = new ArrayList<Expression>();
        while (!tokens.endOfExpression()) {
            exprs.add(new ExpressionParser().parse(tokens));
        }

        if (exprs.size() < 2) {
            throw new ParsingException("S-expression must have at least two subexpressions");
        }

        tokens.expect(Type.CLOSE);

        return new SExpression(exprs);
    }

}
