import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NonogramModel implements Model {
	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;

	public NonogramModel(int[][] rowClues, int[][] colClues) {
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);

		cellStates = new CellState[rowClues.length][colClues.length];
		for (int i = 0; i < rowClues.length; i++) {
			for (int j = 0; j < colClues.length; j++) {
				cellStates[i][j] = CellState.EMPTY;
			}
		}
	}
	
	public static int[][] deepCopy(int[][] original) {
	if (original == null) {
	    return null;
	}
	
	final int[][] result = new int[original.length][];
	for (int i = 0; i < original.length; i++) {
	    result[i] = Arrays.copyOf(original[i], original[i].length);
	
	}
	return result;
	}
	
	
	//Lhanna helped me here
	public NonogramModel(String filename) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileReader(filename));
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		rowClues = new int[rows][];
		colClues = new int[columns][];
		
		sc.nextLine();
		
		String clues;
		
		for (int k = 0; k < rows; k++) {
			clues = sc.nextLine();
			String[] clues2 = clues.split(" ");
			rowClues[k] = new int[clues2.length];
			for (int i = 0; i < clues2.length; i++) {
				rowClues[k][i] = Integer.valueOf(clues2[i]);
			}
		}
		
		for (int k = 0; sc.hasNextLine() && k < columns; k++) {
			clues = sc.nextLine();
			String[] clues2 = clues.trim().split(" ");
			colClues[k] = new int[clues2.length];
			for (int i = 0; i < clues2.length; i++) {
				colClues[k][i] = Integer.parseInt(clues2[i]);
			}
		}
		
		cellStates = new CellState[rowClues.length][colClues.length];
		for (int i = 0; i < rowClues.length; i++) {
			for (int j = 0; j < colClues.length; j++) {
				cellStates[i][j] = CellState.EMPTY;
			}
		}
	}

	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}

	@Override
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {

		if (isSolved() == true || state == null || cellStates[rowIdx][colIdx] == state) {
			return false;
		}
		
		cellStates[rowIdx][colIdx] = state;
		return true;
	}

	@Override
	public int getNumRows() {
		return rowClues.length;
	}

	@Override
	public int getNumCols() {
		return colClues.length;
	}

	@Override
	public int[] getRowClue(int rowIdx) {
//		int[] rowClue = new int[rowClues[rowIdx].length];
//		for (int i = 0; i < rowClue.length; i++) {
//			rowClue[i] = rowClues[rowIdx][i];
//		}
//		return rowClue;
		
		int [] copy = Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
		return copy;
	}

	@Override
	public int[] getColClue(int colIdx) {
		int [] copy = Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
		return copy;
	}
	
	public static List<Integer> project (CellState[] cells){
		List<Integer> list = new ArrayList<>();
		int trueCounter = 0;
		CellState[] checkFalse = new CellState [cells.length];
		
		CellState[] checkMarked = new CellState [cells.length];
		
		Arrays.fill(checkFalse, CellState.EMPTY);
		
		Arrays.fill(checkMarked, CellState.MARKED);
		
//		if (Arrays.equals(checkFalse, cells)) {
//			list.add(0);
//			return list;
//		}
		
		CellState[] checkT = new CellState[cells.length];
		Arrays.fill(checkT, CellState.FILLED);
		
		if (Arrays.equals(checkT, cells)) {
			list.add(cells.length);
			return list;
		}
		
		else if ((Arrays.equals(checkFalse, cells))) {
			list.add(0);
			return list;
		}
		else if (Arrays.equals(checkMarked, cells)) {
			list.add(0);
			return list;
		}
		else {
			
			for (int i = 0; i < cells.length; ++i) { 
				if (cells[i] == CellState.FILLED) {
					trueCounter++; 
					if (i == cells.length-1) {
						list.add(trueCounter);
					}
				}
				 
				else if (cells[i] == CellState.EMPTY || cells[i] == CellState.MARKED){
					if (i > 0 && cells[i-1] == CellState.FILLED) {
						list.add(trueCounter);
						
					}
					trueCounter = 0;
				}
				
			}
	}
		return list;
	}

	@Override
	public boolean isRowSolved(int rowIdx) {
		if (rowIdx > getNumRows()) {
			return false;
		}
		List<Integer>trueList =  new ArrayList<>();
		
		if (rowIdx < cellStates.length) {
			trueList =  project(cellStates[rowIdx]);
		}
		
		ArrayList<Integer> theRows = new ArrayList<Integer>();
		
		for (int i : getRowClue(rowIdx))
		{
		    theRows.add(i);
		}
		
		for (int i = 0; i < trueList.size(); i++) {
			if (trueList.get(i) != getRowClue(rowIdx)[i]) {
				return false;
			}
		}
		
		return true;
	}
	

	
	
	public CellState[] getColumn(CellState[][] array, int index){
		if (array == null || index > array[0].length || index < 0) {
			return null;
		}
	    CellState[] column = new CellState[getNumRows()]; 
	    for(int i=0; i < getNumRows() ; i++){
	       column[i] = getCellState(i, index);
	    
	    }
	    return column;
	}
		
		
	//same logic as isRowSolved()
	@Override
	public boolean isColSolved(int colIdx) {
		
		if (colIdx > getNumCols()) {
			return false;
		}

		CellState [] column = getColumn(cellStates, colIdx);
		List<Integer>trueList =  new ArrayList<>();
		if (colIdx < cellStates.length) {
		trueList =  project(column);
		}
		ArrayList<Integer> theCols = new ArrayList<Integer>();
		for (int i : getColClue(colIdx))
		{
		    theCols.add(i);
		}
		
		if (trueList.equals(theCols)) {
			return true;
		}
		else {
		return false;
		}
		
	}
		

	@Override
	public boolean isSolved() {
		
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); ++j) {
			 if (isRowSolved(i) == false || isColSolved(j) == false) {
				return false;
			}
		
		}
	}
		return true;
	
}
}



