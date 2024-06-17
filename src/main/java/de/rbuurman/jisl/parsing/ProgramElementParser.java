package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.ProgramElement;

public final class ProgramElementParser extends Parser<ProgramElement> {

    @Override
    public ProgramElement parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekSecond().is(SimpleTokenType.DEFINE)) {
            return new DefinitionParser().parse(tokens);
        } else if (tokens.peekSecond().is(SimpleTokenType.REQUIRE)) {
            return new LibraryRequirementParser().parse(tokens);
        } else {
            return new ExpressionParser().parse(tokens);
        }
    }

}
