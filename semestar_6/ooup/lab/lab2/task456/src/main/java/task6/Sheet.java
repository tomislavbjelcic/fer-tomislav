package task6;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sheet {
	
	// da sve koordinate mogu izraziti slovima A-H i brojevima 1-8
	private static final int MAX_ROWS = 8;
	private static final int MAX_COLS = 8;
	private static final String REG = "[A-H][1-8]";
	private static final Pattern PATTERN = Pattern.compile(REG);
	
	private int rows;
	private int cols;
	private Cell[][] cells;
	private ExpressionEvaluator evaluator;
	
	public Sheet(int rows, int cols, ExpressionEvaluator evaluator) {
		setRows(rows);
		setCols(cols);
		cells = new Cell[rows][cols];
		this.evaluator = Objects.requireNonNull(evaluator);
	}
	
	public Sheet(int rows, int cols) {
		this(rows, cols, new DefaultExpressionEvaluator());
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
	
	public double evaluate(String expr) {
		// zamijeni koordinate sa konstantama
		Objects.requireNonNull(expr);
		Matcher matcher = PATTERN.matcher(expr);
		int len = expr.length();
		StringBuilder sb = new StringBuilder(len);
		int lastidx = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			
			sb.append(expr, lastidx, start);
			lastidx = end;
			
			String substr = expr.substring(start, end);
			Cell cell = cell(substr);
			double cellval = cell.getValue();
			
			sb.append(cellval);
		}
		sb.append(expr, lastidx, len);
		String evalexpr = sb.toString();
		
		double result = evaluator.evaluate(evalexpr);
		return result;
	}
	
	private void checkCycles() {}
	
	public void set(String text, String expr) {
		IntPair coords = getCoordinatesFromText(text);
		checkIndex(coords.first, 1, MAX_ROWS, "Row number");
		checkIndex(coords.second, 1, MAX_COLS, "Column number");
		boolean present = cells[coords.first-1][coords.second-1] == null;
		//Cell cell = present ? cells[coords.first-1][coords.second-1] : new Cell(this, expr);
		
		
	}
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("lmao");
		sb.append("lololaxd", 8, 8);
		System.out.println(sb.toString());
	}
	
}
