package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.definition.DefinitionParser;
import de.rbuurman.jisl.parsing.definition.StructDefinitionParser;
import de.rbuurman.jisl.parsing.expression.ExpressionParser;
import de.rbuurman.jisl.program.ProgramElement;

public final class ProgramElementParser extends Parser<ProgramElement> {

    @Override
    public ProgramElement parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peek() == null) {
            throw new ParsingException("Expected ProgramElement, found nothing");
        }

        final Token<?> second = tokens.peekSecond();
        if (second == null) {
            return new ExpressionParser().parse(tokens);
        }

        if (second.is(SimpleTokenType.DEFINE)) {
            return new DefinitionParser().parse(tokens);
        } else if (second.is(SimpleTokenType.DEFINE_STRUCT)) {
            return new StructDefinitionParser().parse(tokens);
        } else if (second.is(SimpleTokenType.REQUIRE)) {
            return new LibraryRequirementParser().parse(tokens);
        } else {
            return new ExpressionParser().parse(tokens);
        }
    }

}
