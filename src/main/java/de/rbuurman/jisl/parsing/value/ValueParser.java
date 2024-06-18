package de.rbuurman.jisl.parsing.value;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.builtin.arithmetic.*;
import de.rbuurman.jisl.program.value.builtin.logic.*;
import de.rbuurman.jisl.program.value.builtin.*;

/**
 * ValueParser
 */
public final class ValueParser extends Parser<Value> {

    @Override
    public Value parse(TokenQueue tokens) throws ParsingException {
        if (tokens.peekSecond().is(SimpleTokenType.LAMBDA)) {
            return new LambdaParser().parse(tokens);
        }

        final var token = tokens.poll();

        if (token.is(SimpleTokenType.IDENTITY)) {
            return new Identity(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.PLUS)) {
            return new Addition(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.MINUS)) {
            return new Subtraction(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.ASTERISK)) {
            return new Multiplication(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.SLASH)) {
            return new Division(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.IF)) {
            return new If(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.AND)) {
            return new And(token.getSourcePosition());
        } else if (token instanceof PrimitiveToken<?> primitive) {
            return primitive.toPrimitive();
        }

        throw new ParsingException("Couldn't parse " + token + " to value", token.getSourcePosition());
    }

}
