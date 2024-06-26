package de.rbuurman.jisl.program;

import de.rbuurman.jisl.utils.SourcePosition;
import de.rbuurman.jisl.utils.Multiple;

public final class StructDefinition extends ProgramElement {
    private VariableName name;
    private Multiple<VariableName> fields;

    public StructDefinition(VariableName name, Multiple<VariableName> fields, SourcePosition sourcePosition) {
        super(sourcePosition);
        this.name = name;
        this.fields = fields;
    }

    public VariableName getName() {
        return name;
    }

    public Multiple<VariableName> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "STRUCT: " + this.name + " -> " + this.fields;
    }

}
