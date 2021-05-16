package hr.fer.zemris.ooup.lab3.texteditor.model;

public class Location {
	
	public int rowIndex;
	public int colIndex;
	
	public Location(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}
	public Location() {
		this(0,0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.getClass() != obj.getClass())
			return false;
		
		Location other = (Location) obj;
		boolean eq = (this.rowIndex == other.rowIndex) 
				&& (this.colIndex == other.colIndex);
		return eq;
	}
	
	@Override
	public String toString() {
		return String.format("[row=%d, col=%d]", rowIndex, colIndex);
	}
	
}
