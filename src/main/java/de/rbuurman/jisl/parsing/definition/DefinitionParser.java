package de.rbuurman.jisl.parsing.definition;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ExpressionParser;
import de.rbuurman.jisl.parsing.VariableNameParser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.Definition;

public final class DefinitionParser extends Parser<Definition> {

    @Override
    public Definition parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekThird().is(SimpleTokenType.OPEN)) {
            return new FunctionDefinitionParser().parse(tokens);
        }

        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.DEFINE);
        var variable = new VariableNameParser().parse(tokens);
        var expression = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new Definition(variable, expression, open.getSourcePosition());
    }

}
