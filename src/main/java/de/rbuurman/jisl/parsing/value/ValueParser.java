package de.rbuurman.jisl.parsing.value;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken;
import de.rbuurman.jisl.lexing.token.Token;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.value.Value;

import java.util.Optional;

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

        if (token instanceof SimpleToken simpleToken) {
            final Optional<Value> value = simpleToken.value();
            if (value.isPresent()) {
                return value.get();
            }
        } else if (token instanceof PrimitiveToken<?> primitive) {
            return primitive.toPrimitive();
        }

        throw new ParsingException("Couldn't parse " + token + " to value", token.getSourcePosition());
    }

}
