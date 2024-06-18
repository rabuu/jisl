package de.rbuurman.jisl.program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.LibraryParser;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.evaluation.Environment;
import de.rbuurman.jisl.program.evaluation.EvaluationException;
import de.rbuurman.jisl.program.expression.Expression;
import de.rbuurman.jisl.program.expression.VariableName;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.SourcePosition;

public abstract class ProgramElement {
    private SourcePosition sourcePosition;

    public SourcePosition getSourcePosition() {
        return this.sourcePosition;
    }

    public Optional<Value> process(Environment environment, Path baseDir)
            throws IOException, LexingException, ParsingException, EvaluationException {
        if (this instanceof LibraryRequirement) {
            final var require = (LibraryRequirement) this;
            final String libraryCode = Files.readString(baseDir.resolve(require.getPath()));
            final Library library = new LibraryParser().parse(libraryCode);
            environment.loadLibrary(library);
        } else if (this instanceof Definition) {
            var definition = (Definition) this;

            VariableName variable = definition.getVariable();
            Value value = definition.getExpression().evaluate(environment);

            environment.addDefinition(variable, value);
        } else if (this instanceof Expression) {
            var expression = (Expression) this;
            return Optional.of(expression.evaluate(environment));
        }

        return Optional.empty();
    }

    @Override
    public abstract String toString();
}
