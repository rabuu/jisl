package de.rbuurman.jisl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Optional;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ProgramElementParser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

/**
 * REPL (Run-Eval-Print-Loop)
 * An interactive "on-the-fly" interpreter
 */
public final class REPL {
    private Environment environment = new Environment();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Run the REPL:
     * it will periodically ask for an input and then interpret it
     * according to the language rules
     */
    public void run() {
        for (;;) {
            try {
                final String input = expectInput(this.reader);

                // quit the REPL if there is no input (e.g. CTRL-D)
                if (input == null)
                    break;

                // quit the REPL by inputting "quit" or "exit"
                if (input.equals("quit") || input.equals("exit"))
                    break;

                // reset the REPL by inputting "reset"
                if (input.equals("reset")) {
                    this.environment.reset();
                    continue;
                }

                // clear the console by inputting "clear"
                if (input.equals("clear")) {
                    System.out.println("\033[H\033[2J");
                    System.out.flush();
                    continue;
                }

                // process the next element
                final var element = new ProgramElementParser().parse(input);
                final Optional<Value> value = element.process(this.environment, Paths.get("."));

                // if element evaluated, print out value
                if (value.isPresent()) {
                    System.out.println(value.get());
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            } catch (LexingException e) {
                System.err.println("Lexing failed: " + e.getMessage());
            } catch (ParsingException e) {
                System.err.println("Parsing failed: " + e.getMessage());
            } catch (EvaluationException e) {
                System.err.println("Evaluating failed: " + e.getMessage());
            }
        }
    }

    private static String expectInput(BufferedReader reader) throws IOException {
        System.out.print("> ");
        return reader.readLine();
    }
}
