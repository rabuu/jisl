package de.rbuurman.jisl.program;

import de.rbuurman.jisl.utils.Multiple;

/**
 * A Library is a collection of Definitions and StructDefinitions
 */
public record Library(Multiple<Definition> definitions, Multiple<StructDefinition> structs) {
}
