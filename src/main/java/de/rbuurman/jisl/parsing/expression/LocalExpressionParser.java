package de.rbuurman.jisl.parsing.expression;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.definition.DefinitionParser;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.expression.LocalExpression;
import de.rbuurman.jisl.utils.Multiple;

/**
 * LocalDefinitionsParser
 */
public final class LocalExpressionParser extends Parser<LocalExpression> {

    @Override
    public LocalExpression parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.LOCAL);
        var open = tokens.expect(SimpleTokenType.OPEN);

        Multiple<Definition> definitions = new Multiple<>();
        while (!tokens.endOfExpression()) {
            definitions.add(new DefinitionParser().parse(tokens));
        }

        if (definitions.size() < 1) {
            throw new ParsingException("At least one local definition must be specified", open.getSourcePosition());
        }
        tokens.expect(SimpleTokenType.CLOSE);

        var expression = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new LocalExpression(definitions, expression);
    }

}
