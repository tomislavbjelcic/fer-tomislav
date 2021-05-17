package hr.fer.zemris.ooup.lab3.texteditor.model;

import java.util.Objects;

public class LocationRange {
	
	public Location dot;
	public Location mark;
	
	public LocationRange(Location dot, Location mark) {
		this.dot = dot;
		this.mark = mark;
	}
	
	public boolean hasSelected() {
		
		return !Objects.equals(dot, mark);
	}
	
	@Override
	public String toString() {
		return String.format("Dot(%d, %d) Mark(%d, %d)", dot.rowIndex, dot.colIndex, mark.rowIndex, mark.colIndex);
	}
	
}
