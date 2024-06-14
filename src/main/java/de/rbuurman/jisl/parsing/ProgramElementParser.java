package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.Type;
import de.rbuurman.jisl.program.ProgramElement;

public final class ProgramElementParser extends Parser<ProgramElement> {

    @Override
    public ProgramElement parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekNth(2).isType(Type.DEFINE)) {
            return new DefinitionParser().parse(tokens);
        } else {
            return new ExpressionParser().parse(tokens);
        }
    }

}
