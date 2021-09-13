import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class PuzzleLoader extends GridPane {
	
	static final int MIN_CELL_LENGTH = 20;
	static final int MAX_CELL_LENGTH = 50;
	static final int DEFAULT_CELL_LENGTH = 30;
	static final int SPACING = 10;
	static final String BUTTON_LABEL = "Load Puzzle";
	static final int HORIZONTAL_GAP = 10;
	static final int VERTICAL_GAP = 10;
	static final String PROMPT = "Enter new filename";
	
	public PuzzleLoader (Main main) {
		setAlignment(Pos.CENTER);
		setHgap(HORIZONTAL_GAP);
		setVgap(VERTICAL_GAP);
		setPadding(new Insets (10, 10, 10, 10));
		
		Label nameFile = new Label ("Nonogram file: ");
		add(nameFile, 0, 0);
		
		
		TextField file = new TextField();
		file.setPromptText(PROMPT);
//		file.setEditable(true);
		add(file, 1, 0);
		
		Label cellLength = new Label ("Cell side length (px): ");
		add(cellLength, 0, 1);
		Spinner<Integer> cells = new Spinner<>(MIN_CELL_LENGTH, DEFAULT_CELL_LENGTH, MAX_CELL_LENGTH);
		add(cells, 1, 1);
		
		Button stBtn = new Button(BUTTON_LABEL);
		add(stBtn, 1, 2);
		
		stBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					String name = "puzzles/" + file.getText();
					NonogramModel model = new NonogramModel(name);
					int cellLength = cells.getValue();
					main.startNonogramPlayer(model, cellLength);
				}
				catch (Exception e) {
					Alert alert1 = new Alert(AlertType.ERROR, "File could not be read.");
					alert1.showAndWait();
				}
			}
		
		});
			
		
		
		
		
	}
	

}

