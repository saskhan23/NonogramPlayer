public class MockModel implements Model {

	public CellState[][] cellStates;
	public boolean[] rowsSolved;
	public boolean[] colsSolved;
	public boolean puzzleSolved;

	public boolean solveRowColOnSet = false;
	public boolean unsolveRowColOnSet = false;
	public boolean solvePuzzleOnSet = false;

	public MockModel() {
		cellStates = new CellState[][] {{CellState.EMPTY}};
		rowsSolved = new boolean[] {false};
		colsSolved = new boolean[] {false};
		puzzleSolved = false;
	}

	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}

	@Override
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if (puzzleSolved || state == cellStates[rowIdx][colIdx]) {
			return false;
		}
		cellStates[rowIdx][colIdx] = state;
		if (solveRowColOnSet) {
			rowsSolved[rowIdx] = true;
			colsSolved[colIdx] = true;
		}
		if (unsolveRowColOnSet) {
			rowsSolved[rowIdx] = false;
			colsSolved[colIdx] = false;
		}
		if (solvePuzzleOnSet) {
			puzzleSolved = true;
		}
		return true;
	}

	@Override
	public int getNumRows() {
		return rowsSolved.length;
	}

	@Override
	public int getNumCols() {
		return colsSolved.length;
	}

	@Override
	public int[] getRowClue(int rowIdx) {
		return null;
	}

	@Override
	public int[] getColClue(int colIdx) {
		return null;
	}

	@Override
	public boolean isRowSolved(int rowIdx) {
		return rowsSolved[rowIdx];
	}

	@Override
	public boolean isColSolved(int colIdx) {
		return colsSolved[colIdx];
	}

	@Override
	public boolean isSolved() {
		return puzzleSolved;
	}
}
