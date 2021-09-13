import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ClueView extends StackPane {

	private static final String STYLE_CLASS = "clue-view";
	private static final String UNSOLVED_STYLE_CLASS = "clue-view-unsolved";
	private static final String SOLVED_STYLE_CLASS = "clue-view-solved";
	private static final double FONT_SIZE_SCALE = 1.0 / 2.0;

	private Rectangle background = new Rectangle();
	private Text text = new Text();

	public ClueView(int sideLength) {
		this("", sideLength);
	}

	public ClueView(int clue, int sideLength) {
		this(Integer.toString(clue), sideLength);
	}

	private ClueView(String clue, int sideLength) {
		getStyleClass().add(STYLE_CLASS);
		text.setText(clue);
		setState(false);
		setSize(sideLength);
		getChildren().addAll(background, text);
	}

	public void setState(boolean solved) {
		ObservableList<String> styleClasses = getStyleClass();
		styleClasses.removeAll(SOLVED_STYLE_CLASS, UNSOLVED_STYLE_CLASS);
		if (solved) {
			styleClasses.add(SOLVED_STYLE_CLASS);
		} else {
			styleClasses.add(UNSOLVED_STYLE_CLASS);
		}
	}

	public void setSize(int sideLength) {
		background.setWidth(sideLength);
		background.setHeight(sideLength);
		text.setFont(new Font(FONT_SIZE_SCALE * sideLength));
	}
}
