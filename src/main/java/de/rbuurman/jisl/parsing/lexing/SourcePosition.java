package de.rbuurman.jisl.parsing.lexing;

public record SourcePosition(int row, int column) {
	@Override
	public String toString() {
		return "[" + this.row + ":" + this.column() + "]";
	}
}
