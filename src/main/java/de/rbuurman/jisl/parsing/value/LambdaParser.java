package de.rbuurman.jisl.parsing.value;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ExpressionParser;
import de.rbuurman.jisl.parsing.VariableNameParser;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.value.Lambda;
import de.rbuurman.jisl.utils.Multiple;

/**
 * LambdaParser
 */
public final class LambdaParser extends Parser<Lambda> {

    @Override
    public Lambda parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.LAMBDA);

        var openVars = tokens.expect(SimpleTokenType.OPEN);

        Multiple<VariableName> vars = new Multiple<>();
        while (!tokens.endOfExpression()) {
            vars.add(new VariableNameParser().parse(tokens));
        }

        if (vars.size() < 1) {
            throw new ParsingException("Lambda must have at least one value", openVars.getSourcePosition());
        }

        tokens.expect(SimpleTokenType.CLOSE);

        var expr = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new Lambda(vars, expr, open.getSourcePosition());
    }

}
