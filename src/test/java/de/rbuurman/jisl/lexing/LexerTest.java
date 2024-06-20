package de.rbuurman.jisl.lexing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.*;
import de.rbuurman.jisl.lexing.token.*;
import de.rbuurman.jisl.lexing.token.SimpleToken.SimpleTokenType;
import de.rbuurman.jisl.parsing.TokenQueue;

public class LexerTest {

	@Test
	void tokenizeCorrectInput() throws LexingException {
		var lexer = new Lexer(
				"(define 1 a -1/2 \"hello world\") ; comment\n [ 0.567 #true] #\\space #\\z 'foo '() empty");
		final TokenQueue tokens = lexer.tokenize();

		final Token<?>[] expected = {
				new SimpleToken(SimpleTokenType.OPEN, null),
				new SimpleToken(SimpleTokenType.DEFINE, null),
				new NumberPrimitive(1.0).toToken(null),
				new VariableNameToken("a", null),
				new NumberPrimitive(-0.5).toToken(null),
				new StringPrimitive("hello world").toToken(null),
				new SimpleToken(SimpleTokenType.CLOSE, null),
				new SimpleToken(SimpleTokenType.OPEN, null),
				new NumberPrimitive(0.567).toToken(null),
				new BooleanPrimitive(true).toToken(null),
				new SimpleToken(SimpleTokenType.CLOSE, null),
				new CharacterPrimitive(' ').toToken(null),
				new CharacterPrimitive('z').toToken(null),
				new SymbolPrimitive("foo").toToken(null),
				new SimpleToken(SimpleTokenType.EMPTY, null),
				new SimpleToken(SimpleTokenType.EMPTY, null),
				new SimpleToken(SimpleTokenType.EOF, null),
		};

		assertArrayEquals(expected, tokens.toArray());
	}

	@Test
	void unterminatedString() {
		var lexer = new Lexer("\"hallo");
		assertThrows(LexingException.class, () -> lexer.tokenize());
	}

	@Test
	void invalidStringEscapeCode() {
		var lexer = new Lexer("\"string\\xhaha\"");
		assertThrows(LexingException.class, () -> lexer.tokenize());
	}

	@Test
	void invalidCharacterLiteral() {
		var lexer = new Lexer("#\\invalid");
		assertThrows(LexingException.class, () -> lexer.tokenize());
	}

	@Test
	void invalidBoolean() {
		var lexer = new Lexer("#foobar");
		assertThrows(LexingException.class, () -> lexer.tokenize());
	}

	@Test
	void invalidFraction() {
		var lexer1 = new Lexer("2/");
		assertThrows(LexingException.class, () -> lexer1.tokenize());
		var lexer2 = new Lexer("2/3/4");
		assertThrows(LexingException.class, () -> lexer2.tokenize());
	}
}
