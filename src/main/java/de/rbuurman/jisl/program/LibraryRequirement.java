package de.rbuurman.jisl.program;

import java.nio.file.Path;

import de.rbuurman.jisl.utils.SourcePosition;

public final class LibraryRequirement extends ProgramElement {
    private Path path;

    public LibraryRequirement(Path path, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "REQUIRE LIBRARY: " + this.path;
    }

}
