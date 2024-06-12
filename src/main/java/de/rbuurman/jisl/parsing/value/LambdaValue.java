package de.rbuurman.jisl.parsing.value;

import java.util.ArrayList;
import java.util.Queue;

import de.rbuurman.jisl.parsing.Expression;
import de.rbuurman.jisl.parsing.Ident;
import de.rbuurman.jisl.parsing.ParsingException;
import de.rbuurman.jisl.parsing.ParsingUtils;
import de.rbuurman.jisl.parsing.lexing.token.SimpleTokenType;
import de.rbuurman.jisl.parsing.lexing.token.Token;

public record LambdaValue(ArrayList<Ident> idents, Expression expr) implements Value {
	public static LambdaValue parse(Queue<Token> tokens) throws ParsingException {
		ParsingUtils.expectToken(tokens, SimpleTokenType.LAMBDA.toToken());
		ParsingUtils.expectToken(tokens, SimpleTokenType.OPEN.toToken());

		var idents = new ArrayList<Ident>();
		while (!tokens.peek().equals(SimpleTokenType.CLOSE.toToken()) && !tokens.peek().exit()) {
			idents.add(Ident.parse(tokens));
		}
		ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

		if (idents.size() == 0) {
			throw new ParsingException("Lambda expression invalid without idents");
		}

		final var expr = Expression.parseExpression(tokens);
		ParsingUtils.expectToken(tokens, SimpleTokenType.CLOSE.toToken());

		return new LambdaValue(idents, expr);
	}
}
