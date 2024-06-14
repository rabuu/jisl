package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.IdentifierToken;
import de.rbuurman.jisl.program.Identifier;

/**
 * IdentifierParser
 */
public final class IdentifierParser extends Parser<Identifier> {

    @Override
    public Identifier parse(TokenQueue tokens) throws ParsingException {
        final var token = tokens.poll();

        if (token instanceof IdentifierToken) {
            return ((IdentifierToken) token).toIdentifier();
        } else {
            throw new ParsingException("No identifier: " + token);
        }
    }

}
