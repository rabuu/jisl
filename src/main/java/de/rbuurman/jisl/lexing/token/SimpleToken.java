package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.builtin.Equality;
import de.rbuurman.jisl.program.builtin.arithmetic.basic.*;
import de.rbuurman.jisl.program.builtin.arithmetic.binary.Exponentiation;
import de.rbuurman.jisl.program.builtin.arithmetic.binary.Modulo;
import de.rbuurman.jisl.program.builtin.arithmetic.comparison.*;
import de.rbuurman.jisl.program.builtin.arithmetic.unary.*;
import de.rbuurman.jisl.program.builtin.list.Cons;
import de.rbuurman.jisl.program.builtin.logic.And;
import de.rbuurman.jisl.program.builtin.logic.Or;
import de.rbuurman.jisl.program.builtin.logic.Not;
import de.rbuurman.jisl.program.builtin.logic.If;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.program.value.list.EmptyList;
import de.rbuurman.jisl.utils.SourcePosition;

import java.util.Optional;

/**
 * A SimpleToken is a Token that actually doesn't carry any data by itself
 * <p>
 * The different simple Tokens are specified by the SimpleTokenType that
 * enumerates all of them
 */
public final class SimpleToken extends Token<SimpleToken.SimpleTokenType> {
	public enum SimpleTokenType {
		// basic syntax
		OPEN,
		CLOSE,

		// keywords
		REQUIRE,
		DEFINE,
		DEFINE_STRUCT,
		LAMBDA,
		LOCAL,

		// list builtins
		EMPTY,
		CONS,

		// arithmetic builtins
		PLUS,
		MINUS,
		ASTERISK,
		SLASH,
		MIN,
		MAX,
		EXP,
		EXPT,
		LOG,
		SQRT,
		CEILING,
		FLOOR,
		MODULO,
		EQUALS,
		LESS,
		LESSEQ,
		GREATER,
		GREATEREQ,

		// logical builtins
		COND,
		ELSE,
		IF,
		AND,
		OR,
		NOT,

		// other builtins
		EQUALITY,
		RANDOM,

		// end of file
		EOF;
	}

	public SimpleToken(SimpleTokenType type, SourcePosition sourcePosition) {
		super(type, sourcePosition);
	}

	public Optional<Value> value() {
		return switch (this.getState()) {
			case EMPTY -> Optional.of(new EmptyList(this.getSourcePosition()));
			case CONS -> Optional.of(new Cons(this.getSourcePosition()));
			case PLUS -> Optional.of(new Addition(this.getSourcePosition()));
			case MINUS -> Optional.of(new Subtraction(this.getSourcePosition()));
			case ASTERISK -> Optional.of(new Multiplication(this.getSourcePosition()));
			case SLASH -> Optional.of(new Division(this.getSourcePosition()));
			case MIN -> Optional.of(new Minimum(this.getSourcePosition()));
			case MAX -> Optional.of(new Maximum(this.getSourcePosition()));
			case EXP -> Optional.of(new NaturalExponentiation(this.getSourcePosition()));
			case EXPT -> Optional.of(new Exponentiation(this.getSourcePosition()));
			case LOG -> Optional.of(new Logarithm(this.getSourcePosition()));
			case SQRT -> Optional.of(new SquareRoot(this.getSourcePosition()));
			case CEILING -> Optional.of(new Ceiling(this.getSourcePosition()));
			case FLOOR -> Optional.of(new Floor(this.getSourcePosition()));
			case MODULO -> Optional.of(new Modulo(this.getSourcePosition()));
			case EQUALS -> Optional.of(new Equals(this.getSourcePosition()));
			case LESS -> Optional.of(new Less(this.getSourcePosition()));
			case LESSEQ -> Optional.of(new LessEq(this.getSourcePosition()));
			case GREATER -> Optional.of(new Greater(this.getSourcePosition()));
			case GREATEREQ -> Optional.of(new GreaterEq(this.getSourcePosition()));
			case IF -> Optional.of(new If(this.getSourcePosition()));
			case AND -> Optional.of(new And(this.getSourcePosition()));
			case OR -> Optional.of(new Or(this.getSourcePosition()));
			case NOT -> Optional.of(new Not(this.getSourcePosition()));
			case EQUALITY -> Optional.of(new Equality(this.getSourcePosition()));
			case RANDOM -> Optional.of(new Random(this.getSourcePosition()));
			default -> Optional.empty();
		};
	}

	@Override
	public boolean is(SimpleTokenType type) {
		return this.getState() == type;
	}

	@Override
	public boolean exit() {
		return this.getState() == SimpleTokenType.EOF;
	}

	@Override
	public String toString() {
		return this.getState().toString();
	}
}
