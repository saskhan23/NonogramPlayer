import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class RowCluesView extends VBox {
	
	private List<RowClueView> rowClueViews;
	
	public RowCluesView(int [][] rowClues, int cellLength, int width) {
		this.rowClueViews = new LinkedList<>();
		for (int [] rowClueView: rowClues) {
			RowClueView rowView = new RowClueView(rowClueView, cellLength, width);
			rowClueViews.add(rowView);
			rowView.setAlignment(Pos.TOP_RIGHT);
			getChildren().add(rowView);
		}
		
	}
	
	public void setRowState(int rowIdx, boolean solved) {
			rowClueViews.get(rowIdx).setState(solved);
	}

}
