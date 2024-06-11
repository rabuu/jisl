package de.rbuurman.jisl.parsing.lexing.token;

public final class KeywordToken extends Token {
	private Keyword keyword;

	public KeywordToken(Keyword keyword) {
		this.keyword = keyword;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	@Override
	public String toString() {
		return "Keyword: " + this.keyword;
	}
}
