package de.rbuurman.jisl.program;

import de.rbuurman.jisl.utils.SourcePosition;

public abstract class ProgramElement {
    private SourcePosition sourcePosition;

    public SourcePosition getSourcePosition() {
        return this.sourcePosition;
    }
}
