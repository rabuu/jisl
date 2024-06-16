package de.rbuurman.jisl.program.builtin;

import de.rbuurman.jisl.program.Value;

/**
 * Builtin
 */
public abstract class Builtin implements Value {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
