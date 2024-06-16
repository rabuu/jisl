package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.builtin.arithmetic.*;
import de.rbuurman.jisl.program.builtin.conditional.*;
import de.rbuurman.jisl.program.builtin.*;

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

        if (token.is(SimpleTokenType.IDENTITY)) {
            return new Identity();
        } else if (token.is(SimpleTokenType.PLUS)) {
            return new Addition();
        } else if (token.is(SimpleTokenType.MINUS)) {
            return new Subtraction();
        } else if (token.is(SimpleTokenType.ASTERISK)) {
            return new Multiplication();
        } else if (token.is(SimpleTokenType.SLASH)) {
            return new Division();
        } else if (token.is(SimpleTokenType.IF)) {
            return new If();
        } else if (token instanceof PrimitiveToken) {
            return ((PrimitiveToken<?>) token).getPrimitive();
        }

        throw new ParsingException("Couldn't parse " + token + " to value", token.getSourcePosition());
    }

}
