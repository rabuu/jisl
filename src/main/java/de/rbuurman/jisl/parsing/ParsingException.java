package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.utils.SourcePosition;

public class ParsingException extends Exception {
    public static ParsingException EmptyTokenQueueException = new ParsingException("Token stack is empty");

    public ParsingException(String err) {
        super(err);
    }

    public ParsingException(String err, SourcePosition sourcePosition) {
        super(err + " at " + sourcePosition);
    }
}
