package de.rbuurman.jisl.parsing;

import java.nio.file.Paths;

import de.rbuurman.jisl.lexing.token.PrimitiveToken;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.program.LibraryRequirement;
import de.rbuurman.jisl.program.value.primitive.StringPrimitive;

/**
 * LibraryRequirementParser
 */
public final class LibraryRequirementParser extends Parser<LibraryRequirement> {

    @Override
    public LibraryRequirement parse(TokenQueue tokens) throws ParsingException {
        var open = tokens.expect(SimpleTokenType.OPEN);
        tokens.expect(SimpleTokenType.REQUIRE);

        final var pathToken = tokens.poll();

        if (!(pathToken instanceof PrimitiveToken pathPrimitive)) {
            throw new ParsingException("Expected string token but got " + pathToken, pathToken.getSourcePosition());
        }

        if (!(pathPrimitive.toPrimitive() instanceof StringPrimitive pathString)) {
            throw new ParsingException("Expected string token but got " + pathToken, pathToken.getSourcePosition());
        }

        tokens.expect(SimpleTokenType.CLOSE);

        return new LibraryRequirement(Paths.get(pathString.getInner()), open.getSourcePosition());
    }

}
