package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.program.Program;

public final class ProgramParser extends Parser<Program> {

    @Override
    public Program parse(TokenQueue tokens) throws ParsingException {
        var program = new Program();

        while (!tokens.finished()) {
            program.queue(new ProgramElementParser().parse(tokens));
        }

        return program;
    }

}
