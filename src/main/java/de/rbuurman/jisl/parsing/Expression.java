package de.rbuurman.jisl.parsing;

import java.util.Queue;

import de.rbuurman.jisl.parsing.lexing.token.Token;

public interface Expression extends ProgramElement {
    public static Expression parse(Queue<Token> tokens) {
        tokens.poll();
        return null;
    }
}
