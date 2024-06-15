package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Value;

/**
 * ValueParser
 */
public final class ValueParser extends Parser<Value> {

    @Override
    public Value parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekNth(2).is(SimpleTokenType.LAMBDA)) {
            return new LambdaParser().parse(tokens);
        }

        final var token = tokens.poll();
        if (token instanceof PrimitiveToken) {
            return ((PrimitiveToken<?>) token).getState();
        }

        throw new ParsingException("Couldn't parse " + token + " to value", token.getSourcePosition());
    }

}
