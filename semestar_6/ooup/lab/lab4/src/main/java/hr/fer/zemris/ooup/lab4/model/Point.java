package hr.fer.zemris.ooup.lab4.model;

public class Point {
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Point translate(Point p) {
		int x = this.x + p.x;
		int y = this.y + p.y;
		return new Point(x, y);
	}
	
	public Point difference(Point p) {
		int x = this.x - p.x;
		int y = this.y - p.y;
		return new Point(x, y);
	}
	
}
