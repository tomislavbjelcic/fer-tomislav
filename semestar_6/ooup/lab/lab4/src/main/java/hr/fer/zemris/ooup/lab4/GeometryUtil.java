package hr.fer.zemris.ooup.lab4;

import hr.fer.zemris.ooup.lab4.model.Point;

public class GeometryUtil {
	
	private GeometryUtil() {}
	
	public static double distanceFromPoint(Point point1, Point point2) {
		int xdiff = point1.getX() - point2.getX();
		int ydiff = point1.getY() - point2.getY();
		return Math.hypot(xdiff, ydiff);
	}
	
	public static double distanceFromLineSegment(Point s, Point e, Point p) {
		double se = distanceFromPoint(s, e);
		if (se == 0.0) {
			return distanceFromPoint(s, p);
		}
		
		int x1 = s.getX();
		int y1 = s.getY();
		int x2 = e.getX();
		int y2 = e.getY();
		int x0 = p.getX();
		int y0 = p.getY();
		
		int numer = Math.abs((x2-x1)*(y1-y0) - (x1-x0)*(y2-y1));
		if (numer == 0) {
			// kolinearne su tocke
			double d1 = distanceFromPoint(s, p);
			double d2 = distanceFromPoint(e, p);
			return Math.min(d1, d2);
		}
		
		double dist = numer / se;
		
		return dist;
	}
	
}
