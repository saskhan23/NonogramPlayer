import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class NonogramView extends BorderPane implements View{
	private RowCluesView left;
	private ColCluesView top;
	private CellGridView center;
	public NonogramView(int[][] rowClues, int[][] colClues, int cellLength) {
		
		RowCluesView newRowView = new RowCluesView(rowClues, cellLength, 4);
		ColCluesView newColView = new ColCluesView(colClues, cellLength, 4);
		CellGridView newCellView = new CellGridView(rowClues.length, colClues.length,cellLength);
		
		center = newCellView;	
		setAlignment(center, Pos.CENTER);
		setCenter(center);
		
		left = newRowView;
		setAlignment(left, Pos.CENTER_LEFT);
		setLeft(left);
		
		top = newColView;
		setAlignment(top, Pos.TOP_CENTER);
		setTop(top);
		
		
	}
	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		center.setCellState(rowIdx, colIdx, state);

	}

	@Override
	public void setRowClueState(int rowIdx, boolean solved) {
		left.setRowState(rowIdx, solved);
		
	}

	@Override
	public void setColClueState(int colIdx, boolean solved) {
		top.setColState(colIdx, solved);
		
	}

	@Override
	public void setPuzzleState(boolean solved) {
		
		if (solved = true) {
			getStylesheets().add("style.css");
		}
		
		else if (solved = false) {
		getStylesheets().remove("style.css");
		}
		
	}

	@Override
	public void register(Presenter presenter) {
		center.register(presenter);
		
	}

}
