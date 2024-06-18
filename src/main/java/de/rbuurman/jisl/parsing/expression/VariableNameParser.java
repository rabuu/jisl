package de.rbuurman.jisl.parsing.expression;

import de.rbuurman.jisl.lexing.token.VariableNameToken;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.expression.VariableName;

/**
 * VariableNameParser
 */
public final class VariableNameParser extends Parser<VariableName> {

    @Override
    public VariableName parse(TokenQueue tokens) throws ParsingException {
        final var token = tokens.poll();

        if (token instanceof VariableNameToken name) {
            return new VariableName(name.getState());
        } else {
            throw new ParsingException("No variable: " + token, token.getSourcePosition());
        }
    }

}
