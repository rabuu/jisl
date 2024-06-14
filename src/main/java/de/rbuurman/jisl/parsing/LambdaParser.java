package de.rbuurman.jisl.parsing;

import java.util.ArrayList;

import de.rbuurman.jisl.lexing.token.SimpleToken.Type;
import de.rbuurman.jisl.program.Identifier;
import de.rbuurman.jisl.program.Lambda;

/**
 * LambdaParser
 */
public final class LambdaParser extends Parser<Lambda> {

    @Override
    public Lambda parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(Type.OPEN);
        tokens.expect(Type.LAMBDA);

        tokens.expect(Type.OPEN);

        var idents = new ArrayList<Identifier>();
        while (!tokens.endOfExpression()) {
            idents.add(new IdentifierParser().parse(tokens));
        }

        if (idents.size() < 1) {
            throw new ParsingException("Lambda Value must have at least one identifier");
        }

        tokens.expect(Type.CLOSE);

        var expr = new ExpressionParser().parse(tokens);
        tokens.expect(Type.CLOSE);

        return new Lambda(idents, expr);
    }

}
