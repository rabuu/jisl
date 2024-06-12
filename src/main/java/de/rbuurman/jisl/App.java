package de.rbuurman.jisl;

import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.Lexer;
import de.rbuurman.jisl.parsing.lexing.LexingException;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public class App {
    public static void main(String[] args) {
        final String text = "#tru";

        try {
            var lexer = new Lexer(text);
            final Stack<Token> tokens = lexer.tokenize();
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (LexingException e) {
            System.err.println(e);
        }
    }
}
