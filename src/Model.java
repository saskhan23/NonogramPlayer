public interface Model {
	CellState getCellState(int rowIdx, int colIdx);
	boolean setCellState(int rowIdx, int colIdx, CellState state);
	int getNumRows();
	int getNumCols();
	int[] getRowClue(int rowIdx);
	int[] getColClue(int colIdx);
	boolean isRowSolved(int rowIdx);
	boolean isColSolved(int colIdx);
	boolean isSolved();
}
