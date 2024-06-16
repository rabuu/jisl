package de.rbuurman.jisl;

import java.nio.file.Files;
import java.nio.file.Path;

import de.rbuurman.jisl.parsing.ProgramParser;
import de.rbuurman.jisl.program.Program;
import de.rbuurman.jisl.program.Value;

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

            for (Value elem : program.run()) {
                System.out.println(elem);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
