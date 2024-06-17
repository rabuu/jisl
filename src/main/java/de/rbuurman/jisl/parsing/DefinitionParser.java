package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Definition;

public final class DefinitionParser extends Parser<Definition> {

    @Override
    public Definition parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekThird().is(SimpleTokenType.OPEN)) {
            return new FunctionDefinitionParser().parse(tokens);
        }
        tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.DEFINE);
        var identifier = new IdentifierParser().parse(tokens);
        var expression = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new Definition(identifier, expression);
    }

}
