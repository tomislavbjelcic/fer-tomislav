package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.GeometryUtil;
import hr.fer.zemris.ooup.lab4.Renderer;

public class Oval extends AbstractGraphicalObject {
	
	private static final Point DEFAULT_ENDPOINT_1 = new Point(10, 0);
	private static final Point DEFAULT_ENDPOINT_2 = new Point(0, 10);
	private static final String SHAPE_NAME = "Oval";
	
	public Oval(Point p1, Point p2) {
		super(new Point[] {p1, p2});
	}
	
	public Oval() {
		this(DEFAULT_ENDPOINT_1, DEFAULT_ENDPOINT_2);
	}

	@Override
	public Rectangle getBoundingBox() {
		Point rhp = this.getHotPoint(0);
		Point bhp = this.getHotPoint(1);
		
		int rx = rhp.getX();
		int ry = rhp.getY();
		int bx = bhp.getX();
		int by = bhp.getY();
		
		int a = rx-bx;
		int b = by-ry;
		
		int width = 2*a;
		int height = 2*b;
		int x = rx - width;
		int y = by - height;
		
		return new Rectangle(x, y, width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Point rhp = this.getHotPoint(0);
		Point bhp = this.getHotPoint(1);
		double hyp = GeometryUtil.distanceFromPoint(rhp, bhp);
		
		int ry = rhp.getY();
		int bx = bhp.getX();
		
		Point center = new Point(bx, ry);
		double d1 = GeometryUtil.distanceFromPoint(mousePoint, center);
		
		double diff = d1-hyp;
		return diff<=0.0 ? 0.0 : diff;
	}

	@Override
	public String getShapeName() {
		return SHAPE_NAME;
	}

	@Override
	public GraphicalObject duplicate() {
		Point p1 = this.getHotPoint(0);
		Point p2 = this.getHotPoint(1);
		GraphicalObject dup = new Oval(p1, p2);
		return dup;
	}

	@Override
	public void render(Renderer r) {
		
		int npoints = 180;
		Point[] points = new Point[npoints];
		Point rhp = this.getHotPoint(0);
		Point bhp = this.getHotPoint(1);
		
		int rx = rhp.getX();
		int ry = rhp.getY();
		int bx = bhp.getX();
		int by = bhp.getY();
		
		int cy = ry;
		int cx = bx;
		int a = rx-bx;
		int b = by-ry;
		
		int x0 = rx;
		int y0 = ry;
		
		for (int i=1; i<=npoints; i++) {
			double ang = 2*Math.PI * i / 180;
			
			int x1 = (int)(cx + a * Math.cos(ang) + 0.5);
			int y1 = (int)(cy + b * Math.sin(ang) + 0.5);
			points[i-1] = new Point(x1, y1);
			
			x0 = x1;
			y0 = y1;
		}
		
		r.fillPolygon(points);
		// renderirati jos ako je oznaceno itd...
	}

}
