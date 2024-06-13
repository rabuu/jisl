package de.rbuurman.jisl.lexing.token;

public final class SimpleToken extends StateToken<SimpleToken.Type> {
	public enum Type {
		OPEN,
		CLOSE,

		PLUS,
		MINUS,

		DEFINE,
		LAMBDA,

		EOF;

		@Override
		public String toString() {
			switch (this) {
				case OPEN:
					return "(";
				case CLOSE:
					return ")";
				case PLUS:
					return "+";
				case MINUS:
					return "-";
				case DEFINE:
					return "define";
				case LAMBDA:
					return "lambda";
				case EOF:
					return "EOF";
				default:
					return null;
			}
		}
	}

	public SimpleToken(Type type) {
		super(type);
	}

	@Override
	public boolean exit() {
		return this.getState() == Type.EOF;
	}
}
