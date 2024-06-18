package de.rbuurman.jisl.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer("(define 1 a \"hello world\") ; comment\n [ 0.567 #true]");
		final TokenQueue tokens = lexer.tokenize();

		final Token<?>[] expected = {
				new SimpleToken(SimpleTokenType.OPEN, null),
				new SimpleToken(SimpleTokenType.DEFINE, null),
				new NumberPrimitive(1.0).toToken(null),
				new VariableNameToken("a", null),
				new StringPrimitive("hello world").toToken(null),
				new SimpleToken(SimpleTokenType.CLOSE, null),
				new CommentToken("comment", null),
				new SimpleToken(SimpleTokenType.OPEN, null),
				new NumberPrimitive(0.567).toToken(null),
				new BooleanPrimitive(true).toToken(null),
				new SimpleToken(SimpleTokenType.CLOSE, null),
				new SimpleToken(SimpleTokenType.EOF, null),
		};

		assertArrayEquals(expected, tokens.toArray());
	}
}
