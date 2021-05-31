package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.GeometryUtil;
import hr.fer.zemris.ooup.lab4.Renderer;

public class LineSegment extends AbstractGraphicalObject {
	
	private static final Point DEFAULT_ENDPOINT_1 = new Point(0, 0);
	private static final Point DEFAULT_ENDPOINT_2 = new Point(10, 0);
	private static final String SHAPE_NAME = "Linija";
	
	public LineSegment(Point p1, Point p2) {
		super(new Point[] {p1, p2});
	}
	
	public LineSegment() {
		this(DEFAULT_ENDPOINT_1, DEFAULT_ENDPOINT_2);
	}

	@Override
	public Rectangle getBoundingBox() {
		Point hp1 = this.getHotPoint(0);
		Point hp2 = this.getHotPoint(1);
		
		int x1 = hp1.getX();
		int y1 = hp1.getY();
		int x2 = hp2.getX();
		int y2 = hp2.getY();
		
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int width = Math.abs(x1-x2);
		int height = Math.abs(y1-y2);
		return new Rectangle(x, y, width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(0), mousePoint);
	}

	@Override
	public String getShapeName() {
		return SHAPE_NAME;
	}

	@Override
	public GraphicalObject duplicate() {
		Point p1 = this.getHotPoint(0);
		Point p2 = this.getHotPoint(1);
		GraphicalObject dup = new LineSegment(p1, p2);
		return dup;
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(this.getHotPoint(0), this.getHotPoint(1));
		// jos
	}

}
