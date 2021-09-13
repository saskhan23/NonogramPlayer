import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final String TITLE = "Nonogram Player";
	private static final boolean RESIZEABLE = false;
	private static final String STYLE_SHEET = "style.css";
	private Stage primaryStage;
	
	public static void main (String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		PuzzleLoader view = new PuzzleLoader(this);
		
		Scene scene = new Scene(view);
		
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(RESIZEABLE);
		primaryStage.show();
		
		
	}
	
	public void startNonogramPlayer(Model model, int cellLength) {
		int[][] rowClues = new int[model.getNumRows()][model.getNumCols()];
		for(int i=0;i<model.getNumRows();i++) {
			rowClues[i] = model.getRowClue(i);
		}
		int[][] colClues = new int[model.getNumCols()][model.getNumRows()];
		for(int j=0;j<model.getNumCols();j++) {
			colClues[j]=model.getColClue(j);
		}
		NonogramView newView = new NonogramView(rowClues, colClues, cellLength);
		Presenter presenter = new Presenter(model, newView);
		newView.register(presenter);
		Scene scene = new Scene(newView);
		scene.getStylesheets().add(STYLE_SHEET);
//		primaryStage = new Stage();
//		primaryStage.setResizable(RESIZEABLE);
		primaryStage.setScene(scene);
//		primaryStage.show();
	}
	

}

