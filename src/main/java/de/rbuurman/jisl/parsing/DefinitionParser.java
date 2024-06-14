package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.Type;
import de.rbuurman.jisl.program.Definition;

public final class DefinitionParser extends Parser<Definition> {

    @Override
    public Definition parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(Type.OPEN);
        tokens.expect(Type.DEFINE);
        var identifier = new IdentifierParser().parse(tokens);
        var expression = new ExpressionParser().parse(tokens);
        tokens.expect(Type.CLOSE);

        return new Definition(identifier, expression);
    }

}
