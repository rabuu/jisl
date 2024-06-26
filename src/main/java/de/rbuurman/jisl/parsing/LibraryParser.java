package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.definition.DefinitionParser;
import de.rbuurman.jisl.parsing.definition.StructDefinitionParser;
import de.rbuurman.jisl.parsing.expression.ParsingException;
import de.rbuurman.jisl.program.Definition;
import de.rbuurman.jisl.program.Library;
import de.rbuurman.jisl.program.StructDefinition;
import de.rbuurman.jisl.utils.Multiple;

public final class LibraryParser extends Parser<Library> {

    @Override
    public Library parse(TokenQueue tokens) throws ParsingException {
        Multiple<Definition> definitions = new Multiple<>();
        Multiple<StructDefinition> structs = new Multiple<>();

        while (!tokens.finished()) {
            if (tokens.peekSecond().is(SimpleTokenType.DEFINE_STRUCT)) {
                structs.add(new StructDefinitionParser().parse(tokens));
            } else {
                definitions.add(new DefinitionParser().parse(tokens));
            }
        }

        return new Library(definitions, structs);
    }

}
