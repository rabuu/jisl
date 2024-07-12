package de.rbuurman.jisl.parsing.expression;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.ProgramElementParser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.ProgramElement;
import de.rbuurman.jisl.program.StructDefinition;
import de.rbuurman.jisl.program.expression.LocalExpression;
import de.rbuurman.jisl.utils.Multiple;

/**
 * LocalDefinitionsParser
 */
public final class LocalExpressionParser extends Parser<LocalExpression> {

    @Override
    public LocalExpression parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.LOCAL);
        var openDefinitions = tokens.expect(SimpleTokenType.OPEN);

        Multiple<Definition> definitions = new Multiple<>();
        Multiple<StructDefinition> structs = new Multiple<>();
        while (!tokens.endOfExpression()) {
            ProgramElement e = new ProgramElementParser().parse(tokens);
            if (e instanceof Definition def) {
                definitions.add(def);
            } else if (e instanceof StructDefinition struct) {
                structs.add(struct);
            } else {
                throw new ParsingException("Expected definitions or struct definitions",
                        openDefinitions.getSourcePosition());
            }
        }

        tokens.expect(SimpleTokenType.CLOSE);

        var expression = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new LocalExpression(definitions, structs, expression, open.getSourcePosition());
    }

}
