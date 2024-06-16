package de.rbuurman.jisl.parsing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.Identifier;
import de.rbuurman.jisl.program.Lambda;

/**
 * LambdaParser
 */
public final class LambdaParser extends Parser<Lambda> {

    @Override
    public Lambda parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.LAMBDA);

        var open = tokens.expect(SimpleTokenType.OPEN);

        Queue<Identifier> idents = new LinkedList<>();
        while (!tokens.endOfExpression()) {
            idents.add(new IdentifierParser().parse(tokens));
        }

        if (idents.size() < 1) {
            throw new ParsingException("Lambda Value must have at least one identifier", open.getSourcePosition());
        }

        tokens.expect(SimpleTokenType.CLOSE);

        var expr = new ExpressionParser().parse(tokens);
        tokens.expect(SimpleTokenType.CLOSE);

        return new Lambda(idents, expr);
    }

}
