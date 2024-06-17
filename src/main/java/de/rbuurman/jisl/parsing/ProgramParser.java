package de.rbuurman.jisl.parsing;

import java.nio.file.Path;

import de.rbuurman.jisl.program.Program;

public final class ProgramParser extends Parser<Program> {
    private Path baseDir;

    public ProgramParser(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public Program parse(TokenQueue tokens) throws ParsingException {
        var program = new Program(baseDir);

        while (!tokens.finished()) {
            program.queue(new ProgramElementParser().parse(tokens));
        }

        return program;
    }

}
