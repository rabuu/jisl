package de.rbuurman.jisl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.ProgramParser;
import de.rbuurman.jisl.program.Program;

public class Main {
    public static String USAGE = "USAGE: jisl <SOURCE-FILE>";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.exit(1);
        }

        try {
            final String code = Files.readString(Path.of(args[0]));
            System.out.print(code);
            System.out.println("-----------------------------------------------------");

            final Program program = new ProgramParser().parse(code);

            for (Object elem : program.toArray()) {
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
