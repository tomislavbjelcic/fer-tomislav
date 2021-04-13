package task6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Cell implements CellListener {
	
	private String expr;
	private double value;
	private Sheet sheet;
	private String regex;
	private String[] operands;
	
	private List<Cell> referenced = new ArrayList<>();
	private List<CellListener> listeners = new LinkedList<>();
	
	public Cell(Sheet sheet, String expr, String regex) {
		this.sheet = Objects.requireNonNull(sheet);
		this.regex = Objects.requireNonNull(regex);
		setExpr(expr);
	}
	
	public double evaluate() {
		double oldval = value;
		double newval = 0.0;
		for (String op : operands) {
			if (!op.matches(regex)) {
				newval += Double.parseDouble(op);
				continue;
			}
			
			Cell c = sheet.cell(op);
			newval += c.getValue();
			
		}
		
		value = newval;
		if (oldval != newval)
			notifyListeners();
		return newval;
	}
	
	public void setExpr(String expr) {
		Objects.requireNonNull(expr);
		for (Cell r : referenced) {
			r.removeCellListener(this);
		}
		referenced.clear();
		operands = expr.split("\\+");
		for (String op : operands) {
			if (op.matches(regex)) {
				Cell c = sheet.cell(op);
				referenced.add(c);
				c.addCellListener(this);
			}
		}
		this.expr = expr;
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
	
	public void addCellListener(CellListener cl) {
		listeners.add(Objects.requireNonNull(cl));
	}
	
	public boolean removeCellListener(CellListener cl) {
		return listeners.remove(cl);
	}
	
	private void notifyListeners() {
		listeners.forEach(l -> l.cellChanged(this));
	}
	
	@Override
	public String toString() {
		return String.format("[%s = %f]", expr, value);
	}

	@Override
	public void cellChanged(Cell c) {
		this.evaluate();
	}
	
}
