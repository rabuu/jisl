package de.rbuurman.jisl.parsing;

import java.util.ArrayList;

import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Expression;
import de.rbuurman.jisl.program.SExpression;

/**
 * SExpressionParser
 */
public final class SExpressionParser extends Parser<SExpression> {

    @Override
    public SExpression parse(TokenQueue tokens) throws ParsingException {
        Token open = tokens.expect(SimpleTokenType.OPEN);

        var exprs = new ArrayList<Expression>();
        while (!tokens.endOfExpression()) {
            exprs.add(new ExpressionParser().parse(tokens));
        }

        if (exprs.size() < 2) {
            throw new ParsingException("S-expression must have at least two subexpressions", open.getSourcePosition());
        }

        tokens.expect(SimpleTokenType.CLOSE);

        return new SExpression(exprs);
    }

}
