import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.HBox;

public class RowClueView extends HBox {

	private static final String STYLE_CLASS = "row-clue-view";

	private List<ClueView> clueViews = new LinkedList<>();

	public RowClueView(int[] rowClue, int cellLength, int width) {
		getStyleClass().add(STYLE_CLASS);
		for (int clue : rowClue) {
			clueViews.add(new ClueView(clue, cellLength));
		}
		while (clueViews.size() < width) {
			clueViews.add(0, new ClueView(cellLength));
		}
		getChildren().addAll(clueViews);
		setMaxWidth(USE_PREF_SIZE);
		setMaxHeight(USE_PREF_SIZE);
	}

	public void setState(boolean solved) {
		for (ClueView clueView : clueViews) {
			clueView.setState(solved);
		}
	}
}
