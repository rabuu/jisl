package de.rbuurman.jisl.parsing.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Queue;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.parsing.lexing.token.*;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer("(define 1 a) ; comment\n [ 0.567 #true]");
		final Queue<Token> tokens = lexer.tokenize();

		final Token[] expected = {
				SimpleTokenType.OPEN.toToken(),
				SimpleTokenType.DEFINE.toToken(),
				new IntegerToken(1, null),
				new IdentToken("a", null),
				SimpleTokenType.CLOSE.toToken(),
				new CommentToken("comment", null),
				SimpleTokenType.OPEN.toToken(),
				new FloatToken(0.567f, null),
				new BooleanToken(true, null),
				SimpleTokenType.CLOSE.toToken(),
				SimpleTokenType.EOF.toToken(),
		};

		assertArrayEquals(expected, tokens.toArray());
	}
}
