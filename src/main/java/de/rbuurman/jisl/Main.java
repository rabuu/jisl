package de.rbuurman.jisl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

import de.rbuurman.jisl.parsing.lexing.Lexer;
import de.rbuurman.jisl.parsing.lexing.LexingException;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public class Main {
    public static String USAGE = "USAGE: jisl <SOURCE-FILE>";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.exit(1);
        }

        try {
            final String code = Files.readString(Path.of(args[0]));
            System.out.println(code);
            System.out.println("-----------------------------------------------------");

            final var lexer = new Lexer(code);
            final Stack<Token> tokens = lexer.tokenize();

            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LexingException e) {
            throw new RuntimeException(e);
        }
    }
}
