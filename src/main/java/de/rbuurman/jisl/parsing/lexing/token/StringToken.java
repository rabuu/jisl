package de.rbuurman.jisl.parsing.lexing.token;

import de.rbuurman.jisl.parsing.lexing.SourcePosition;
import de.rbuurman.jisl.elements.value.StringValue;
import de.rbuurman.jisl.elements.value.Value;

public final class StringToken extends StateToken<String> implements ValueToken {
	public StringToken(String string, SourcePosition sourcePosition) {
		super(string, sourcePosition);
	}

	@Override
	public boolean compareState(String cmp) {
		return this.getState().equals(cmp);
	}

	@Override
	public Value toValue() {
		return new StringValue(this.getState());
	}
}
