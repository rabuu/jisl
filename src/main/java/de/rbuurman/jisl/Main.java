package de.rbuurman.jisl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.rbuurman.jisl.lexing.Lexer;
import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.ProgramParser;
import de.rbuurman.jisl.parsing.TokenQueue;
import de.rbuurman.jisl.program.Program;
import de.rbuurman.jisl.program.Value;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.Multiple;

public class Main {
    public static String USAGE = "USAGE: jisl [repl|inspect-lexing|inspect-parsing] <SOURCE-FILE>";

    public static void main(final String[] args) {
        try {
            run(args);
        } catch (CLIException e) {
            System.err.println(e.getMessage());
            System.err.println(USAGE);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't read file: " + e.getMessage());
            System.exit(1);
        } catch (LexingException e) {
            System.err.println("Lexing failed: " + e.getMessage());
            System.exit(1);
        } catch (ParsingException e) {
            System.err.println("Parsing failed: " + e.getMessage());
            System.exit(1);
        } catch (EvaluationException e) {
            System.err.println("Evaluation failed: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void run(final String[] args)
            throws CLIException, IOException, LexingException, ParsingException, EvaluationException {
        if (args.length < 1) {
            throw new CLIException("Expected at least one argument");
        }

        String code;
        switch (args[0]) {
            case "repl":
                if (args.length != 1) {
                    throw new CLIException("REPL doesn't take any arguments");
                }
                new REPL().run();
                return;
            case "inspect-lexing":
                if (args.length != 2) {
                    throw new CLIException("Expected second argument SOURCE-FILE");
                }
                code = readCodeFile(args[1]);
                inspectLexing(code);
                return;

            case "inspect-parsing":
                if (args.length != 2) {
                    throw new CLIException("Expected second argument SOURCE-FILE");
                }
                code = readCodeFile(args[1]);
                inspectParsing(code);
                return;

            default:
                if (args.length != 1) {
                    throw new CLIException("Too many arguments");
                }
                code = readCodeFile(args[0]);
                runProgram(code);
                break;
        }
    }

    private static void inspectLexing(final String code) throws LexingException {
        TokenQueue tokens = new Lexer(code).tokenize();
        for (var token : tokens) {
            System.out.println(token);
        }
    }

    private static void inspectParsing(final String code) throws LexingException, ParsingException {
        Program program = new ProgramParser().parse(code);
        for (var element : program) {
            System.out.println(element);
        }
    }

    private static void runProgram(final String code)
            throws IOException, LexingException, ParsingException, EvaluationException {
        Program program = new ProgramParser().parse(code);
        Multiple<Value> values = program.run();

        for (var value : values) {
            System.out.println(value);
        }
    }

    private static String readCodeFile(final String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
