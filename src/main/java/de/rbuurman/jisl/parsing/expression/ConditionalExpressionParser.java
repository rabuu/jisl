package de.rbuurman.jisl.parsing.expression;

import java.util.Optional;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.expression.ConditionExpression;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.utils.Multiple;

/**
 * ConditionalParser
 */
public final class ConditionalExpressionParser extends Parser<ConditionExpression> {

    @Override
    public ConditionExpression parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.COND);

        Multiple<Expression[]> conds = new Multiple<>();
        Optional<Expression> elseClause = Optional.empty();
        while (!tokens.endOfExpression()) {
            tokens.expect(SimpleTokenType.OPEN);

            if (tokens.peek().is(SimpleTokenType.ELSE)) {
                tokens.expect(SimpleTokenType.ELSE);
                elseClause = Optional.of(new ExpressionParser().parse(tokens));
                tokens.expect(SimpleTokenType.CLOSE);
                break;
            }

            var pred = new ExpressionParser().parse(tokens);
            var expr = new ExpressionParser().parse(tokens);
            Expression[] cond = { pred, expr };
            conds.add(cond);

            tokens.expect(SimpleTokenType.CLOSE);
        }

        tokens.expect(SimpleTokenType.CLOSE);

        if (elseClause.isEmpty() && conds.size() < 1) {
            throw new ParsingException("Empty conditionals are not allowed", open.getSourcePosition());
        }

        return new ConditionExpression(conds, elseClause);
    }

}
