package de.rbuurman.jisl.utils;

/**
 * SourcePosition holds information on where in the source code
 * something is located
 */
public record SourcePosition(int row, int column) {

	/**
	 * Get the source position of the next row after the current position
	 * (resets column)
	 */
	public SourcePosition nextRow() {
		return new SourcePosition(this.row() + 1, 1);
	}

	/**
	 * Get the source position of the next column after the current position
	 */
	public SourcePosition nextColumn() {
		return new SourcePosition(this.row(), this.column() + 1);
	}

	@Override
	public String toString() {
		return this.row + ":" + this.column();
	}

}
