package task6;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class Sheet {
	
	// da sve koordinate mogu izraziti slovima A-H i brojevima 1-8
	private static final int MAX_ROWS = 8;
	private static final int MAX_COLS = 8;
	private static final String REG = "[A-H][1-8]";
	
	private int rows;
	private int cols;
	private Cell[][] cells;
	
	public Sheet(int rows, int cols) {
		setRows(rows);
		setCols(cols);
		cells = new Cell[rows][cols];
	}
	
	
	private static void checkIndex(int i, int lower, int upper, String str) {
		if (i<lower || i>upper)
			throw new IllegalArgumentException(String.format("%s out of [%d, %d] range: %d",
													str, lower, upper, i));
	}
	
	private void setRows(int rows) {
		checkIndex(rows, 1, MAX_ROWS, "Rows");
		this.rows = rows;
	}
	
	private void setCols(int cols) {
		checkIndex(cols, 1, MAX_COLS, "Cols");
		this.cols = cols;
	}
	
	public static IntPair getCoordinatesFromText(String text) {
		Objects.requireNonNull(text);
		if (!text.matches(REG))
			throw new IllegalArgumentException("Invalid coordinates: " + text);
		
		char letter = text.charAt(0);
		char num = text.charAt(1);
		int row = (letter - 'A') + 1;
		int col = num - '0';
		
		return new IntPair(row, col);
	}
	
	
	public Cell cell(String text) {
		IntPair coords = getCoordinatesFromText(text);
		checkIndex(coords.first, 1, MAX_ROWS, "Row number");
		checkIndex(coords.second, 1, MAX_COLS, "Column number");
		return cells[coords.first-1][coords.second-1];
	}
	
	public double evaluate(Cell cell) {
		return cell.evaluate();
	}
	
	private static void checkCircular(Cell c) {
		
		Deque<Cell> stack = new LinkedList<>();
		
		for (Cell r : c.getReferencedCells())
			stack.push(r);
		
		while(!stack.isEmpty()) {
			Cell top = stack.pop();
			if (top.equals(c))
				throw new RuntimeException("Circular dependency.");
			
			var refd = top.getReferencedCells();
			for (Cell r : refd)
				stack.push(r);
			
		}
	}
	
	public void set(String text, String expr) {
		IntPair coords = getCoordinatesFromText(text);
		checkIndex(coords.first, 1, MAX_ROWS, "Row number");
		checkIndex(coords.second, 1, MAX_COLS, "Column number");
		boolean present = cells[coords.first-1][coords.second-1] != null;
		Cell cell = present ? cells[coords.first-1][coords.second-1] : new Cell(this, expr, REG);
		
		cell.setExpr(expr);
		
		checkCircular(cell);
		
		
		if (!present)
			cells[coords.first-1][coords.second-1] = cell;
		cell.evaluate();
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				Cell c = cells[i][j];
				sb.append((c==null) ? "[EMPTY]" : c);
				sb.append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
}
