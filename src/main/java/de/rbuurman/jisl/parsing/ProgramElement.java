package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public interface ProgramElement {
    public static ProgramElement parseProgramElement(Queue<Token> tokens) throws ParsingException {
        boolean removedOpen = false;
        if (tokens.peek().equals(SimpleTokenType.OPEN.toToken())) {
            removedOpen = true;
            tokens.remove();
        }

        boolean definition = false;
        if (tokens.peek().equals(SimpleTokenType.DEFINE.toToken()))
            definition = true;

        if (definition) {
            return Definition.parse(tokens, removedOpen);
        } else {
            return Expression.parseExpression(tokens, removedOpen);
        }
    }
}
