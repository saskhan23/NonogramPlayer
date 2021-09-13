import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ColCluesView extends HBox {
	
	private List<ColClueView> colClueViews;
	
	public ColCluesView(int [][] colClues, int cellLength, int height) {
		this.colClueViews = new LinkedList<>();
		for (int [] colClueView: colClues) {
			ColClueView colView = new ColClueView(colClueView, cellLength, height);
			colClueViews.add(colView);
			setAlignment(Pos.BOTTOM_RIGHT);
			getChildren().add(colView);
		}
		
	}
	
	public void setColState(int colIdx, boolean solved) {
			colClueViews.get(colIdx).setState(solved);
	}

}
