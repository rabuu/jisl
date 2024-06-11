package de.rbuurman.jisl;

import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.Lexer;
import de.rbuurman.jisl.parsing.lexing.LexingException;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public class App {
    public static void main(String[] args) {
        final String text = "; comment\n(define a 1)\n(#true and #false true-false 1.0 0.1 1.)[]";

        try {
            final Stack<Token> tokens = Lexer.tokenize(text);
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (LexingException e) {
            System.err.println(e);
        }
    }
}
