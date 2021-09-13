import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class PresenterTest {

	@Test
	void testRegisterWithView() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		Presenter presenter = new Presenter(model, view);
		assertEquals(view.presenter, presenter);
	}

	@Test
	void testSynchronizeCellViewStates() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		model.cellStates[0][0] = CellState.FILLED;
		view.cellStates[0][0] = CellState.EMPTY;
		new Presenter(model, view);
		assertEquals(CellState.FILLED, model.cellStates[0][0]);
		assertEquals(CellState.FILLED, view.cellStates[0][0]);

		model.cellStates[0][0] = CellState.MARKED;
		view.cellStates[0][0] = CellState.EMPTY;
		new Presenter(model, view);
		assertEquals(CellState.MARKED, model.cellStates[0][0]);
		assertEquals(CellState.MARKED, view.cellStates[0][0]);

		model.cellStates[0][0] = CellState.EMPTY;
		view.cellStates[0][0] = CellState.FILLED;
		new Presenter(model, view);
		assertEquals(CellState.EMPTY, model.cellStates[0][0]);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);

		model.cellStates = new CellState[][] {
				{CellState.EMPTY, CellState.FILLED},
				{CellState.FILLED, CellState.MARKED}};
		model.rowsSolved = new boolean[] {false, false};
		model.colsSolved = new boolean[] {false, false};
		model.puzzleSolved = false;
		view.cellStates = new CellState[][] {
				{CellState.FILLED, CellState.EMPTY},
				{CellState.MARKED, CellState.FILLED}};
		view.rowClueStates = new boolean[] {false, false};
		view.colClueStates = new boolean[] {false, false};
		view.puzzleState = false;

		new Presenter(model, view);
		assertEquals(CellState.EMPTY, model.cellStates[0][0]);
		assertEquals(CellState.FILLED, model.cellStates[0][1]);
		assertEquals(CellState.FILLED, model.cellStates[1][0]);
		assertEquals(CellState.MARKED, model.cellStates[1][1]);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);
		assertEquals(CellState.FILLED, view.cellStates[0][1]);
		assertEquals(CellState.FILLED, view.cellStates[1][0]);
		assertEquals(CellState.MARKED, view.cellStates[1][1]);
	}

	@Test
	void testSynchronizeClueViewStates() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		model.rowsSolved[0] = true;
		model.colsSolved[0] = true;
		view.rowClueStates[0] = false;
		view.colClueStates[0] = false;
		new Presenter(model, view);
		assertTrue(view.rowClueStates[0]);
		assertTrue(view.colClueStates[0]);

		model.rowsSolved[0] = false;
		model.colsSolved[0] = false;
		view.rowClueStates[0] = true;
		view.colClueStates[0] = true;
		new Presenter(model, view);
		assertFalse(view.rowClueStates[0]);
		assertFalse(view.colClueStates[0]);

		model.cellStates = new CellState[][] {
				{CellState.EMPTY, CellState.EMPTY},
				{CellState.EMPTY, CellState.EMPTY}};
		model.rowsSolved = new boolean[] {false, true};
		model.colsSolved = new boolean[] {false, true};
		model.puzzleSolved = false;
		view.cellStates = new CellState[][] {
				{CellState.EMPTY, CellState.EMPTY},
				{CellState.EMPTY, CellState.EMPTY}};
		view.rowClueStates = new boolean[] {false, false};
		view.colClueStates = new boolean[] {true, true};
		view.puzzleState = false;

		new Presenter(model, view);
		assertFalse(view.rowClueStates[0]);
		assertTrue(view.rowClueStates[1]);
		assertFalse(view.colClueStates[0]);
		assertTrue(view.colClueStates[1]);
	}

	@Test
	void testSynchronizePuzzleState() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		model.cellStates[0][0] = CellState.FILLED;
		model.rowsSolved[0] = true;
		model.colsSolved[0] = true;
		model.puzzleSolved = true;
		view.cellStates[0][0] = CellState.EMPTY;
		view.rowClueStates[0] = false;
		view.colClueStates[0] = false;
		view.puzzleState = false;

		new Presenter(model, view);
		assertEquals(CellState.FILLED, view.cellStates[0][0]);
		assertTrue(view.puzzleState);

		model.cellStates[0][0] = CellState.FILLED;
		model.rowsSolved[0] = false;
		model.colsSolved[0] = false;
		model.puzzleSolved = false;
		view.cellStates[0][0] = CellState.EMPTY;
		view.rowClueStates[0] = true;
		view.colClueStates[0] = true;
		view.puzzleState = true;

		new Presenter(model, view);
		assertEquals(CellState.FILLED, view.cellStates[0][0]);
		assertFalse(view.puzzleState);

		model.cellStates[0][0] = CellState.MARKED;
		model.rowsSolved[0] = true;
		model.colsSolved[0] = true;
		model.puzzleSolved = true;
		view.cellStates[0][0] = CellState.FILLED;
		view.rowClueStates[0] = false;
		view.colClueStates[0] = false;
		view.puzzleState = false;

		// Change MARKED cell views to EMPTY after the puzzle is solved.
		new Presenter(model, view);
		assertEquals(CellState.MARKED, model.cellStates[0][0]);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);
		assertTrue(view.puzzleState);

		model.cellStates = new CellState[][] {
				{CellState.EMPTY, CellState.MARKED},
				{CellState.MARKED, CellState.FILLED}};
		model.rowsSolved = new boolean[] {true, true};
		model.colsSolved = new boolean[] {true, true};
		model.puzzleSolved = true;
		view.cellStates = new CellState[][] {
				{CellState.FILLED, CellState.FILLED},
				{CellState.MARKED, CellState.MARKED}};
		view.rowClueStates = new boolean[] {false, false};
		view.colClueStates = new boolean[] {false, false};
		view.puzzleState = false;

		new Presenter(model, view);
		assertEquals(CellState.EMPTY, model.cellStates[0][0]);
		assertEquals(CellState.MARKED, model.cellStates[0][1]);
		assertEquals(CellState.MARKED, model.cellStates[1][0]);
		assertEquals(CellState.FILLED, model.cellStates[1][1]);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);
		assertEquals(CellState.EMPTY, view.cellStates[0][1]);
		assertEquals(CellState.EMPTY, view.cellStates[1][0]);
		assertEquals(CellState.FILLED, view.cellStates[1][1]);
		assertTrue(view.puzzleState);
	}

	@Test
	void testUpdateModel() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		Presenter presenter = new Presenter(model, view);

		model.cellStates[0][0] = CellState.EMPTY;
		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.FILLED, model.cellStates[0][0]);

		model.cellStates[0][0] = CellState.EMPTY;
		presenter.cellClicked(0, 0, false);
		assertEquals(CellState.MARKED, model.cellStates[0][0]);

		model.cellStates[0][0] = CellState.FILLED;
		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.EMPTY, model.cellStates[0][0]);

		model.cellStates[0][0] = CellState.FILLED;
		presenter.cellClicked(0, 0, false);
		assertEquals(CellState.MARKED, model.cellStates[0][0]);

		model.cellStates[0][0] = CellState.MARKED;
		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.FILLED, model.cellStates[0][0]);

		model.cellStates[0][0] = CellState.MARKED;
		presenter.cellClicked(0, 0, false);
		assertEquals(CellState.EMPTY, model.cellStates[0][0]);
	}

	@Test
	void testUpdateView1() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		Presenter presenter = new Presenter(model, view);

		initMockObjects(model, view, new CellState[][] {{CellState.EMPTY}},
				new boolean[] {false}, new boolean[] {false}, false);
		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.FILLED, view.cellStates[0][0]);

		initMockObjects(model, view, new CellState[][] {{CellState.EMPTY}},
				new boolean[] {true}, new boolean[] {true}, true);
		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);

		initMockObjects(model, view,
				new CellState[][] {
					{CellState.FILLED, CellState.EMPTY},
					{CellState.MARKED, CellState.FILLED}},
				new boolean[] {false, false}, new boolean[] {false, false},
				false);

		presenter.cellClicked(0, 0, true);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.EMPTY},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.MARKED, CellState.FILLED},
				view.cellStates[1]);

		presenter.cellClicked(0, 1, false);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.MARKED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.MARKED, CellState.FILLED},
				view.cellStates[1]);

		presenter.cellClicked(1, 0, true);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.MARKED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.FILLED},
				view.cellStates[1]);

		presenter.cellClicked(1, 1, false);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.MARKED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.MARKED},
				view.cellStates[1]);
	}

	@Test
	void testUpdateView2() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		Presenter presenter = new Presenter(model, view);

		initMockObjects(model, view, new CellState[][] {{CellState.FILLED}},
				new boolean[] {false}, new boolean[] {false}, false);
		model.solveRowColOnSet = true;
		model.unsolveRowColOnSet = false;

		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);
		assertTrue(view.rowClueStates[0]);
		assertTrue(view.colClueStates[0]);

		initMockObjects(model, view,
				new CellState[][] {{CellState.EMPTY, CellState.EMPTY}},
				new boolean[] {false}, new boolean[] {true, false}, false);
		model.solveRowColOnSet = false;
		model.unsolveRowColOnSet = true;

		presenter.cellClicked(0, 0, true);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.EMPTY},
				view.cellStates[0]);
		assertArrayEquals(new boolean[] {false}, view.rowClueStates);
		assertArrayEquals(new boolean[] {false, false}, view.colClueStates);

		initMockObjects(model, view,
				new CellState[][] {
					{CellState.FILLED, CellState.EMPTY},
					{CellState.EMPTY, CellState.FILLED}},
				new boolean[] {false, false}, new boolean[] {false, false},
				false);
		model.solveRowColOnSet = true;
		model.unsolveRowColOnSet = false;

		presenter.cellClicked(0, 1, true);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.FILLED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.FILLED},
				view.cellStates[1]);
		assertArrayEquals(new boolean[] {true, false}, view.rowClueStates);
		assertArrayEquals(new boolean[] {false, true}, view.colClueStates);

		presenter.cellClicked(1, 0, true);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.FILLED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.FILLED, CellState.FILLED},
				view.cellStates[1]);
		assertArrayEquals(new boolean[] {true, true}, view.rowClueStates);
		assertArrayEquals(new boolean[] {true, true}, view.colClueStates);
	}

	@Test
	void testUpdateView3() {
		MockModel model = new MockModel();
		MockView view = new MockView();
		Presenter presenter = new Presenter(model, view);

		initMockObjects(model, view, new CellState[][] {{CellState.MARKED}},
				new boolean[] {false}, new boolean[] {false}, false);
		model.solveRowColOnSet = true;
		model.unsolveRowColOnSet = false;
		model.solvePuzzleOnSet = true;

		presenter.cellClicked(0, 0, true);
		assertEquals(CellState.FILLED, view.cellStates[0][0]);
		assertTrue(view.rowClueStates[0]);
		assertTrue(view.colClueStates[0]);
		assertTrue(view.puzzleState);

		initMockObjects(model, view, new CellState[][] {{CellState.FILLED}},
				new boolean[] {false}, new boolean[] {false}, false);
		model.solveRowColOnSet = true;
		model.unsolveRowColOnSet = false;
		model.solvePuzzleOnSet = true;

		presenter.cellClicked(0, 0, false);
		assertEquals(CellState.MARKED, model.cellStates[0][0]);
		assertEquals(CellState.EMPTY, view.cellStates[0][0]);
		assertTrue(view.rowClueStates[0]);
		assertTrue(view.colClueStates[0]);
		assertTrue(view.puzzleState);

		initMockObjects(model, view,
				new CellState[][] {
					{CellState.FILLED, CellState.MARKED},
					{CellState.EMPTY, CellState.MARKED}},
				new boolean[] {false, false}, new boolean[] {false, false},
				false);
		model.solveRowColOnSet = true;
		model.unsolveRowColOnSet = false;
		model.solvePuzzleOnSet = false;

		presenter.cellClicked(0, 0, false);
		assertArrayEquals(new CellState[] {CellState.MARKED, CellState.MARKED},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.MARKED},
				view.cellStates[1]);
		assertArrayEquals(new boolean[] {true, false}, view.rowClueStates);
		assertArrayEquals(new boolean[] {true, false}, view.colClueStates);
		assertFalse(view.puzzleState);

		// Solve the puzzle on the second move.
		model.solvePuzzleOnSet = true;
		presenter.cellClicked(1, 1, true);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.EMPTY},
				view.cellStates[0]);
		assertArrayEquals(new CellState[] {CellState.EMPTY, CellState.FILLED},
				view.cellStates[1]);
		assertArrayEquals(new boolean[] {true, true}, view.rowClueStates);
		assertArrayEquals(new boolean[] {true, true}, view.colClueStates);
		assertTrue(view.puzzleState);
	}

	private static void initMockObjects(MockModel model, MockView view,
			CellState[][] cellStates, boolean[] rowsSolved,
			boolean[] colsSolved, boolean puzzleSolved) {
		model.cellStates = new CellState[cellStates.length][];
		view.cellStates = new CellState[cellStates.length][];
		for (int rowIdx = 0; rowIdx < cellStates.length; ++rowIdx) {
			CellState[] row = cellStates[rowIdx];
			model.cellStates[rowIdx] = Arrays.copyOf(row, row.length);
			view.cellStates[rowIdx] = Arrays.copyOf(row, row.length);
		}
		model.rowsSolved = Arrays.copyOf(rowsSolved, rowsSolved.length);
		view.rowClueStates = Arrays.copyOf(rowsSolved, rowsSolved.length);
		model.colsSolved = Arrays.copyOf(colsSolved, colsSolved.length);
		view.colClueStates = Arrays.copyOf(colsSolved, colsSolved.length);
		model.puzzleSolved = puzzleSolved;
		view.puzzleState = puzzleSolved;
	}
}
