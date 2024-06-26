package de.rbuurman.jisl.program;

import de.rbuurman.jisl.utils.Multiple;

/**
 * Library
 */
public record Library(Multiple<Definition> definitions, Multiple<StructDefinition> structs) {
}
