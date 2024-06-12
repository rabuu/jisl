package de.rbuurman.jisl.parsing.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Queue;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.primitive.*;
import de.rbuurman.jisl.parsing.lexing.token.*;
import de.rbuurman.jisl.parsing.lexing.token.SimpleToken.Type;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer("(define 1 a \"hello world\") ; comment\n [ 0.567 #true]");
		final Queue<Token> tokens = lexer.tokenize();

		final Token[] expected = {
				new SimpleToken(Type.OPEN),
				new SimpleToken(Type.DEFINE),
				new IntegerPrimitive(1).toToken(),
				new IdentToken("a"),
				new StringPrimitive("hello world").toToken(),
				new SimpleToken(Type.CLOSE),
				new CommentToken("comment"),
				new SimpleToken(Type.OPEN),
				new FloatPrimitive(0.567f).toToken(),
				new BooleanPrimitive(true).toToken(),
				new SimpleToken(Type.CLOSE),
				new SimpleToken(Type.EOF),
		};

		assertArrayEquals(expected, tokens.toArray());
	}
}
