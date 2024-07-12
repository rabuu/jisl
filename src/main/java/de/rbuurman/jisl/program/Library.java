package de.rbuurman.jisl.program;

import java.io.IOException;
import java.nio.file.Path;

import de.rbuurman.jisl.lexing.Lexer;
import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ProgramElementParser;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;

/**
 * A Library is a Environment that can be included by a Program
 */
public record Library(Environment environment, Path path) {
    public Library(String code, Path path) throws IOException, EvaluationException, LexingException, ParsingException {
        this(new Environment(), path);

        var tokens = new Lexer(code).tokenize();

        while (!tokens.finished()) {
            var element = new ProgramElementParser().parse(tokens);
            element.process(this.environment(), this.path().getParent());
        }
    }
}
