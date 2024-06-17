package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.Identifier;
import de.rbuurman.jisl.program.Lambda;
import de.rbuurman.jisl.utils.Multiple;

/**
 * FunctionDefinitionParser
 */
public final class FunctionDefinitionParser extends Parser<Definition> {

    @Override
    public Definition parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.DEFINE);
        tokens.expect(SimpleTokenType.OPEN);

        var name = new IdentifierParser().parse(tokens);

        Multiple<Identifier> args = new Multiple<>();
        while (!tokens.endOfExpression()) {
            args.add(new IdentifierParser().parse(tokens));
        }
        tokens.expect(SimpleTokenType.CLOSE);

        var expression = new ExpressionParser().parse(tokens);

        tokens.expect(SimpleTokenType.CLOSE);

        final var lambda = new Lambda(args, expression);
        return new Definition(name, lambda);
    }

}
