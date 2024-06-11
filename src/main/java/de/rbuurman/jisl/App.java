package de.rbuurman.jisl;

import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.TokenCursor;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public class App {
    public static void main(String[] args) {
        final String text = "; comment\nhahaha";
        final Stack<Token> tokens = TokenCursor.tokenize(text);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
