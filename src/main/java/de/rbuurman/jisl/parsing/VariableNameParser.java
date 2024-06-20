package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.VariableNameToken;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.VariableName;

/**
 * VariableNameParser
 */
public final class VariableNameParser extends Parser<VariableName> {

    @Override
    public VariableName parse(TokenQueue tokens) throws ParsingException {
        final var token = tokens.poll();

        if (token instanceof VariableNameToken name) {
            return new VariableName(name.toInner(), token.getSourcePosition());
        } else {
            throw new ParsingException("No variable: " + token, token.getSourcePosition());
        }
    }

}
