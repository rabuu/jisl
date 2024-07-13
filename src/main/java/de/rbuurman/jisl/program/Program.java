package de.rbuurman.jisl.program;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.PeekableQueue;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Program
 * <br>
 * This is the type that holds the entirety of a parsed program and can run it.
 * A Program is a Queue of ProgramElements (it extends PeekableQueue).
 * It remembers where in the file system it lives (for library support).
 * And a Program holds the global Environment that is used for evaluating.
 */
public final class Program extends PeekableQueue<ProgramElement> {
    private Path baseDir;
    private Environment environment = new Environment();

    public Program(Path baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Run the program.
     * <br>
     * It goes through the elements and processes them according to their type.
     * See ProgramElement.process().
     */
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
