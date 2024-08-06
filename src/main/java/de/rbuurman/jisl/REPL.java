package de.rbuurman.jisl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Optional;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ProgramElementParser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.LibraryRequirement;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

/**
 * REPL (Run-Eval-Print-Loop)
 * An interactive "on-the-fly" interpreter
 */
public final class REPL {
    private final Environment environment = new Environment();
    private final LineReader reader;

    public REPL() throws IOException {
        final Terminal terminal = TerminalBuilder.terminal();
        final Completer completer = new StringsCompleter("quit", "exit", "reset", "std", "clear");

        this.reader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();
    }

    /**
     * Run the REPL:
     * it will periodically ask for an input and then interpret it
     * according to the language rules
     */
    public void run() {
        for (;;) {
            try {
                final String input = reader.readLine("> ");

                // quit the REPL if there is no input (e.g. CTRL-D)
                if (input == null)
                    break;

                // quit the REPL by inputting "quit" or "exit"
                if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit"))
                    break;

                // reset the REPL by inputting "reset"
                if (input.equalsIgnoreCase("reset")) {
                    this.environment.reset();
                    continue;
                }

                // shorthand for (require "stdlib.rkt")
                if (input.equalsIgnoreCase("std")) {
                    new LibraryRequirement(Paths.get("stdlib.rkt"), null).process(environment, Paths.get("."));
                    continue;
                }

                // clear the console by inputting "clear"
                if (input.equalsIgnoreCase("clear")) {
                    final Terminal term = this.reader.getTerminal();
                    term.puts(Capability.clear_screen);
                    term.flush();
                    continue;
                }

                // process the next element
                final var element = new ProgramElementParser().parse(input);
                final Optional<Value> value = element.process(this.environment, Paths.get("."));

                // if element evaluated, print out value
                value.ifPresent(System.out::println);
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
}
