package de.rbuurman.jisl.parsing.definition;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.VariableNameParser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.StructDefinition;
import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.utils.Multiple;

/**
 * StructDefinitionParser
 */
public final class StructDefinitionParser extends Parser<StructDefinition> {

    @Override
    public StructDefinition parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.DEFINE_STRUCT);
        var name = new VariableNameParser().parse(tokens);

        tokens.expect(SimpleTokenType.OPEN);
        Multiple<VariableName> fields = new Multiple<>();
        while (!tokens.endOfExpression()) {
            fields.add(new VariableNameParser().parse(tokens));
        }

        tokens.expect(SimpleTokenType.CLOSE);
        tokens.expect(SimpleTokenType.CLOSE);

        return new StructDefinition(name, fields, open.getSourcePosition());
    }

}
