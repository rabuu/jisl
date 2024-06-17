package de.rbuurman.jisl.parsing;

import java.nio.file.Paths;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.LibraryRequirement;
import de.rbuurman.jisl.program.primitive.StringPrimitive;

/**
 * LibraryRequirementParser
 */
public final class LibraryRequirementParser extends Parser<LibraryRequirement> {

    @Override
    public LibraryRequirement parse(TokenQueue tokens) throws ParsingException {
        tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.REQUIRE);

        final var pathToken = tokens.poll();

        if (!(pathToken instanceof PrimitiveToken)) {
            throw new ParsingException("Expected string token but got " + pathToken, pathToken.getSourcePosition());
        }

        final var pathPrimitive = ((PrimitiveToken<?>) pathToken).getPrimitive();

        if (!(pathPrimitive instanceof StringPrimitive)) {
            throw new ParsingException("Expected string token but got " + pathToken, pathToken.getSourcePosition());
        }

        final var pathString = ((StringPrimitive) pathPrimitive).getInner();

        tokens.expect(SimpleTokenType.CLOSE);

        return new LibraryRequirement(Paths.get(pathString));
    }

}
