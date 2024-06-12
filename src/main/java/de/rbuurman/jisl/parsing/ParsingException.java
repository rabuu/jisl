package de.rbuurman.jisl.parsing;

public class ParsingException extends Exception {
    public static ParsingException EmptyTokenQueueException = new ParsingException("Token stack is empty");

    public ParsingException(String err) {
        super(err);
    }
}
