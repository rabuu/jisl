package de.rbuurman.jisl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;

import de.rbuurman.jisl.element.ProgramElement;
import de.rbuurman.jisl.parsing.Parser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.lexing.LexingException;

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

            final var parser = new Parser(code);
            final Queue<ProgramElement> program = parser.parse();

            for (ProgramElement elem : program) {
                System.out.println(elem);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LexingException e) {
            throw new RuntimeException(e);
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
    }
}
