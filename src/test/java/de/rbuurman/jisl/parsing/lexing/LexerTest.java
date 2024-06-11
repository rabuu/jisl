package de.rbuurman.jisl.parsing.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.parsing.lexing.token.*;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer("(define 1 a) ; comment\n [ 0.567 #true]");
		final Stack<Token> tokens = lexer.tokenize();

		final Token[] expected = {
				new SimpleToken(SimpleTokenType.PAREN_OPEN),
				new SimpleToken(SimpleTokenType.DEFINE),
				new IntegerToken(1),
				new IdentToken("a"),
				new SimpleToken(SimpleTokenType.PAREN_CLOSE),
				new CommentToken("comment"),
				new SimpleToken(SimpleTokenType.BRACKET_OPEN),
				new FloatToken(0.567f),
				new BooleanToken(true),
				new SimpleToken(SimpleTokenType.BRACKET_CLOSE),
				new SimpleToken(SimpleTokenType.EOF),
		};

		assertArrayEquals(expected, tokens.toArray());
	}
}
