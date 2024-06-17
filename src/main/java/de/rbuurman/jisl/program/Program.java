package de.rbuurman.jisl.program;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.utils.PeekableQueue;
import de.rbuurman.jisl.utils.Multiple;

public final class Program extends PeekableQueue<ProgramElement> {
    private Path baseDir;
    private Environment environment = new Environment();

    public Program(Path baseDir) {
        this.baseDir = baseDir;
    }

    public Multiple<Value> run() throws EvaluationException, IOException, ParsingException, LexingException {
        Multiple<Value> results = new Multiple<>();

        for (ProgramElement element : this.elements) {
            final Optional<Value> value = element.process(this.environment, baseDir);
            if (value.isPresent()) {
                results.add(value.get());
            }
        }

        return results;
    }
}
