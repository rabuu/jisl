package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public interface ProgramElement {
    public static ProgramElement parseProgramElement(Queue<Token> tokens) throws ParsingException {
        if (tokens.peek().equals(SimpleTokenType.OPEN.toToken())) {
            tokens.remove();
        }

        boolean definition = false;
        if (tokens.peek().equals(SimpleTokenType.DEFINE.toToken()))
            definition = true;

        if (definition) {
            System.err.println("DEFINITION");
            return Definition.parse(tokens);
        } else {
            return null;
        }
    }
}
