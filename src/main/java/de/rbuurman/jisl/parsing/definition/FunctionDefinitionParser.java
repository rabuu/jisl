package de.rbuurman.jisl.parsing.definition;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ExpressionParser;
import de.rbuurman.jisl.parsing.expression.IdentifierParser;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.expression.Identifier;
import de.rbuurman.jisl.program.value.Lambda;
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
