package de.rbuurman.jisl.parsing;

import java.util.LinkedList;
import java.util.Queue;

import de.rbuurman.jisl.element.*;
import de.rbuurman.jisl.lexing.Lexer;
import de.rbuurman.jisl.lexing.LexingException;
import de.rbuurman.jisl.lexing.token.CommentToken;
import de.rbuurman.jisl.lexing.token.Token;

public final class Parser {
    private Queue<Token> tokens;

    public Parser(Queue<Token> tokens) {
        this.tokens = tokens;
    }

    public Parser(String text) throws LexingException {
        final var lexer = new Lexer(text);
        this.tokens = lexer.tokenize();
    }

    public Queue<ProgramElement> parse() throws ParsingException {
        var program = new LinkedList<ProgramElement>();
        while (!tokens.isEmpty() && !tokens.peek().exit()) {
            if (tokens.peek() instanceof CommentToken) {
                tokens.remove();
                continue;
            }

            program.add(ProgramElementParser.parseProgramElement(this.tokens));
        }
        return program;
    }

}
