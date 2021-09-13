
public class Presenter {
	
	private Model model;
	private View view;
	
	//Jose Saldivar helped me here
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
	

		
		view.setPuzzleState(model.isSolved());
//		if (model.isSolved()) {
//			for (int i = 0; i < model.getNumRows(); i++) {
//				for (int j = 0; j < model.getNumCols(); j++) {
//					view.setCellState(i, j, model.getCellState(i, j));
//				}
//			}
//			for (int i = 0; i < model.getNumRows(); i++) {
//				for (int j = 0; j < model.getNumCols(); j++) {
//					if (model.getCellState(i, j) == CellState.MARKED) {
//					view.setCellState(i, j, CellState.EMPTY);
//					}
//				}
//			}
//		
//	
//		}
		//the below test case is commented out because there is a bug, where when the test case is included the 
		//program will not load
		//however, the junit tests pass because of it 
		
//			for (int i = 0; i < model.getNumRows(); i++){
//				for (int j = 0; j < model.getNumCols(); j++) {
//					view.setCellState(i, j, model.getCellState(i, j));
//				}
//			}
//		
//			for (int i = 0; i < model.getNumRows(); ++i) {
//				if (model.isRowSolved(i)) {
//					view.setRowClueState(i, true);
//				}
//					else {
//						view.setRowClueState(i, false);
//					}
//				}
//			for (int i = 0; i < model.getNumCols(); ++i) {
//				if (model.isColSolved(i)) {
//					view.setColClueState(i, true);
//				}
//					else {
//						view.setColClueState(i, false);
//					}
//				}
		if (model.isSolved()) {
			for (int i = 0; i < model.getNumRows(); i++) {
				for (int j = 0; j < model.getNumCols(); j++) {
					view.setCellState(i, j, model.getCellState(i, j));
				}
			}
			for (int i = 0; i < model.getNumRows(); i++) {
				for (int j = 0; j < model.getNumCols(); j++) {
					if (model.getCellState(i, j) == CellState.MARKED) {
					view.setCellState(i, j, CellState.EMPTY);
					}
				}
			}
		
	
		}
		
		
		this.view.register(this);
		}
		
		
		



		
	
	
	public void cellClicked(int rowIdx, int colIdx, boolean primaryButton) {
		
		CellState state = model.getCellState(rowIdx, colIdx);
		CellState selected = null;
		boolean updated = false;
		if (primaryButton) {
			if (state == CellState.EMPTY || state == CellState.MARKED) {
				updated = model.setCellState(rowIdx, colIdx, CellState.FILLED);
				selected = CellState.FILLED;
			}
			else {
				updated = model.setCellState(rowIdx, colIdx, CellState.EMPTY);
				selected = CellState.EMPTY;
			}
		}
		else {
			if (state == CellState.EMPTY || state == CellState.FILLED) {
				updated = model.setCellState(rowIdx, colIdx, CellState.MARKED);
				selected = CellState.MARKED;
			}
			else {
				updated = model.setCellState(rowIdx, colIdx, CellState.EMPTY);
				selected = CellState.EMPTY;
			}
		}
		
		if (updated) {
			view.setCellState(rowIdx, colIdx, selected);
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
			view.setPuzzleState(model.isSolved());
		}
		
		if (model.isSolved()) {
			for (int index = 0; index < model.getNumRows(); ++index) {
				for (int i = 0; i < model.getNumCols(); ++i) {
				if(model.getCellState(index, i) == CellState.MARKED) {
					view.setCellState(index, i, CellState.EMPTY);
				}
			}
			}
		}
		
	}
	
}



