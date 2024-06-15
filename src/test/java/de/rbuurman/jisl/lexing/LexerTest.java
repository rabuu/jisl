package de.rbuurman.jisl.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.primitive.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer("(define 1 a \"hello world\") ; comment\n [ 0.567 #true]");
		final TokenQueue tokens = lexer.tokenize();

		final Token[] expected = {
				new SimpleToken(SimpleTokenType.OPEN),
				new SimpleToken(SimpleTokenType.DEFINE),
				new IntegerPrimitive(1).toToken(),
				new IdentifierToken("a"),
				new StringPrimitive("hello world").toToken(),
				new SimpleToken(SimpleTokenType.CLOSE),
				new CommentToken("comment"),
				new SimpleToken(SimpleTokenType.OPEN),
				new FloatPrimitive(0.567f).toToken(),
				new BooleanPrimitive(true).toToken(),
				new SimpleToken(SimpleTokenType.CLOSE),
				new SimpleToken(SimpleTokenType.EOF),
		};

		assertArrayEquals(expected, tokens.toArray());
	}
}
