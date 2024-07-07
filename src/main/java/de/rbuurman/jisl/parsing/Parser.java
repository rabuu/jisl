package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.Lexer;
import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.expression.ParsingException;

/**
 * Parser defines a common interface for class that turn Tokens into
 * objects that are meaningful to the program,
 * e.g. ProgramElements like Definitions or Expressions
 */
public abstract class Parser<T> {

    /**
     * Turn Tokens into T
     */
    public abstract T parse(TokenQueue tokens) throws ParsingException;

    /**
     * Turn a String into T by lexing it first
     */
    public T parse(final String text) throws LexingException, ParsingException {
        final var lexer = new Lexer(text);
        final TokenQueue tokens = lexer.tokenize();
        return parse(tokens);
    }
}
