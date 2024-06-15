package de.rbuurman.jisl.utils;

public record SourcePosition(int row, int column) {

	public SourcePosition nextRow() {
		return new SourcePosition(this.row() + 1, 1);
	}

	public SourcePosition nextColumn() {
		return new SourcePosition(this.row(), this.column() + 1);
	}

	@Override
	public String toString() {
		return "[" + this.row + ":" + this.column() + "]";
	}

}
