package de.rbuurman.jisl.parsing.definition;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ExpressionParser;
import de.rbuurman.jisl.parsing.expression.VariableNameParser;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.expression.VariableName;
import de.rbuurman.jisl.program.value.Lambda;
import de.rbuurman.jisl.utils.Multiple;

/**
 * FunctionDefinitionParser
 */
public final class FunctionDefinitionParser extends Parser<Definition> {

    @Override
    public Definition parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.DEFINE);
        tokens.expect(SimpleTokenType.OPEN);

        var name = new VariableNameParser().parse(tokens);

        Multiple<VariableName> args = new Multiple<>();
        while (!tokens.endOfExpression()) {
            args.add(new VariableNameParser().parse(tokens));
        }
        tokens.expect(SimpleTokenType.CLOSE);

        var expression = new ExpressionParser().parse(tokens);

        tokens.expect(SimpleTokenType.CLOSE);

        final var lambda = new Lambda(args, expression, name.getSourcePosition());
        return new Definition(name, lambda, open.getSourcePosition());
    }

}
