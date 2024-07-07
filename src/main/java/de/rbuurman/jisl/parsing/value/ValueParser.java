package de.rbuurman.jisl.parsing.value;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.builtin.*;
import de.rbuurman.jisl.program.builtin.arithmetic.*;
import de.rbuurman.jisl.program.builtin.logic.*;
import de.rbuurman.jisl.program.builtin.list.*;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.EmptyList;

/**
 * ValueParser
 */
public final class ValueParser extends Parser<Value> {

    @Override
    public Value parse(TokenQueue tokens) throws ParsingException {
        final Token<?> second = tokens.peekSecond();
        if (second != null) {
            if (second.is(SimpleTokenType.LAMBDA)) {
                return new LambdaParser().parse(tokens);
            }
        }

        final var token = tokens.poll();

        if (token.is(SimpleTokenType.IDENTITY)) {
            return new Identity(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.EQUALITY)) {
            return new Equality(token.getSourcePosition());
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
        } else if (token.is(SimpleTokenType.EMPTY)) {
            return new EmptyList(token.getSourcePosition());
        } else if (token.is(SimpleTokenType.CONS)) {
            return new ListConstructor(token.getSourcePosition());
        } else if (token instanceof PrimitiveToken<?> primitive) {
            return primitive.toPrimitive();
        }

        throw new ParsingException("Couldn't parse " + token + " to value", token.getSourcePosition());
    }

}
