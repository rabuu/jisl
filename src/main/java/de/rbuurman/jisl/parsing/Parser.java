package de.rbuurman.jisl.parsing;

import de.rbuurman.jisl.lexing.Lexer;
import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.parsing.expression.ParsingException;

public abstract class Parser<T> {
    public abstract T parse(TokenQueue tokens) throws ParsingException;

    public T parse(String text) throws LexingException, ParsingException {
        var lexer = new Lexer(text);
        final TokenQueue tokens = lexer.tokenize();
        return parse(tokens);
    }
}
