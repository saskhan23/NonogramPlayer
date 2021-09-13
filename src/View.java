public interface View {
	void setCellState(int rowIdx, int colIdx, CellState state);
	void setRowClueState(int rowIdx, boolean solved);
	void setColClueState(int colIdx, boolean solved);
	void setPuzzleState(boolean solved);
	void register(Presenter presenter);
}
