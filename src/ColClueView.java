import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.VBox;

public class ColClueView extends VBox {

	private static final String STYLE_CLASS = "col-clue-view";

	private List<ClueView> clueViews = new LinkedList<>();

	public ColClueView(int[] colClue, int cellLength, int height) {
		getStyleClass().add(STYLE_CLASS);
		for (int clue : colClue) {
			clueViews.add(new ClueView(clue, cellLength));
		}
		while (clueViews.size() < height) {
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
