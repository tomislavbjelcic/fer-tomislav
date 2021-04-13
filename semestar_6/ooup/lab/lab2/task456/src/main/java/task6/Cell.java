package task6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Cell {
	
	private String expr;
	private double value;
	private ExpressionEvaluator evaluator;
	private String regex;
	
	private List<Cell> referenced = new ArrayList<>();
	private List<CellListener> listeners = new LinkedList<>();
	
	public Cell(String expr, String regex, ExpressionEvaluator evaluator) {
		this.expr = Objects.requireNonNull(expr);
		this.regex = Objects.requireNonNull(regex);
		this.evaluator = Objects.requireNonNull(evaluator);
		this.value = evaluate();
	}
	
	public double evaluate() {
		return 0.0;
	}
	
	public String getExpr() {
		return expr;
	}

	public double getValue() {
		return value;
	}

	public List<Cell> getReferencedCells() {
		return referenced;
	}
	
}
