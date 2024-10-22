package de.rbuurman.jisl.lexing.token;

import de.rbuurman.jisl.program.builtin.equality.*;
import de.rbuurman.jisl.program.builtin.arithmetic.basic.*;
import de.rbuurman.jisl.program.builtin.arithmetic.binary.*;
import de.rbuurman.jisl.program.builtin.arithmetic.comparison.*;
import de.rbuurman.jisl.program.builtin.arithmetic.unary.*;
import de.rbuurman.jisl.program.builtin.is.*;
import de.rbuurman.jisl.program.builtin.list.*;
import de.rbuurman.jisl.program.builtin.logic.*;
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
		LIST,
		MAKE_LIST,
		CAR,
		CDR,
		APPEND,
		APPLY,

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
		REMAINDER,
		ARITHMETIC_EQUALITY,
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

		// is of type ...? builtins
		IS_FALSE,
		IS_BOOLEAN,
		IS_NUMBER,
		IS_INTEGER,
		IS_SYMBOL,
		IS_LIST,
		IS_CONS,
		IS_EMPTY,
		IS_CHARACTER,
		IS_STRING,
		IS_STRUCT,
		IS_PROCEDURE,

		// other builtins
		STRICT_EQUALITY,
		STRUCTURAL_EQUALITY,
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
			case LIST -> Optional.of(new ListConstructor(this.getSourcePosition()));
			case MAKE_LIST -> Optional.of(new MakeList(this.getSourcePosition()));
			case CAR -> Optional.of(new Car(this.getSourcePosition()));
			case CDR -> Optional.of(new Cdr(this.getSourcePosition()));
			case APPEND -> Optional.of(new Append(this.getSourcePosition()));
			case APPLY -> Optional.of(new Apply(this.getSourcePosition()));
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
			case REMAINDER -> Optional.of(new Remainder(this.getSourcePosition()));			
			case ARITHMETIC_EQUALITY -> Optional.of(new ArithmeticEquality(this.getSourcePosition()));
			case LESS -> Optional.of(new Less(this.getSourcePosition()));
			case LESSEQ -> Optional.of(new LessEq(this.getSourcePosition()));
			case GREATER -> Optional.of(new Greater(this.getSourcePosition()));
			case GREATEREQ -> Optional.of(new GreaterEq(this.getSourcePosition()));
			case IF -> Optional.of(new If(this.getSourcePosition()));
			case AND -> Optional.of(new And(this.getSourcePosition()));
			case OR -> Optional.of(new Or(this.getSourcePosition()));
			case NOT -> Optional.of(new Not(this.getSourcePosition()));
			case IS_FALSE -> Optional.of(new IsFalse(this.getSourcePosition()));
			case IS_BOOLEAN -> Optional.of(new IsBoolean(this.getSourcePosition()));
			case IS_NUMBER -> Optional.of(new IsNumber(this.getSourcePosition()));
			case IS_INTEGER -> Optional.of(new IsInteger(this.getSourcePosition()));
			case IS_SYMBOL -> Optional.of(new IsSymbol(this.getSourcePosition()));
			case IS_LIST -> Optional.of(new IsList(this.getSourcePosition()));
			case IS_CONS -> Optional.of(new IsCons(this.getSourcePosition()));
			case IS_EMPTY -> Optional.of(new IsEmpty(this.getSourcePosition()));
			case IS_CHARACTER -> Optional.of(new IsCharacter(this.getSourcePosition()));
			case IS_STRING -> Optional.of(new IsString(this.getSourcePosition()));
			case IS_STRUCT -> Optional.of(new IsStruct(this.getSourcePosition()));
			case IS_PROCEDURE -> Optional.of(new IsProcedure(this.getSourcePosition()));
			case STRICT_EQUALITY -> Optional.of(new StrictEquality(this.getSourcePosition()));
			case STRUCTURAL_EQUALITY -> Optional.of(new StructuralEquality(this.getSourcePosition()));
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
