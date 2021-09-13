public class MockView implements View {

	public CellState[][] cellStates;
	public boolean[] rowClueStates;
	public boolean[] colClueStates;
	public boolean puzzleState;
	public Presenter presenter;

	public MockView() {
		cellStates = new CellState[][] {{CellState.EMPTY}};
		rowClueStates = new boolean[] {false};
		colClueStates = new boolean[] {false};
		puzzleState = false;
	}

	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellStates[rowIdx][colIdx] = state;
	}

	@Override
	public void setRowClueState(int rowIdx, boolean solved) {
		rowClueStates[rowIdx] = solved;
	}

	@Override
	public void setColClueState(int colIdx, boolean solved) {
		colClueStates[colIdx] = solved;
	}

	@Override
	public void setPuzzleState(boolean solved) {
		puzzleState = solved;
	}

	@Override
	public void register(Presenter presenter) {
		this.presenter = presenter;
	}
}
