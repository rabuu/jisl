package de.rbuurman.jisl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import de.rbuurman.jisl.parsing.ProgramElementParser;
import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.Environment;

/**
 * REPL
 */
public final class REPL {
    private Environment environment = new Environment();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void run() throws IOException {
        loop: for (;;) {

            final String input = expectInput(this.reader);
            if (input == null)
                break loop;

            final String[] exitSequences = { "quit", "exit", "(quit)", "(exit)" };
            for (String seq : exitSequences) {
                if (input.equals(seq))
                    break loop;
            }

            try {
                final var element = new ProgramElementParser().parse(input);
                final Optional<Value> value = element.process(this.environment);

                if (value.isPresent()) {
                    System.out.println(value.get());
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static String expectInput(BufferedReader reader) throws IOException {
        System.out.print("> ");
        return reader.readLine();
    }
}
