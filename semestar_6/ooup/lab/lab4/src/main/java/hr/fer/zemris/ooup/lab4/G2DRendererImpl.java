package hr.fer.zemris.ooup.lab4;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.ooup.lab4.model.Point;

public class G2DRendererImpl implements Renderer {
	
	private static final Color B = Color.BLUE;
	private static final Color R = Color.RED;
	
	private Graphics2D g2d;
	
	public void setG2D(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		Color c = g2d.getColor();
		g2d.setColor(B);
		g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
		g2d.setColor(c);
	}

	@Override
	public void fillPolygon(Point[] points) {
		Color c = g2d.getColor();
		g2d.setColor(B);
		
		int len = points.length;
		int[] xcords = new int[len];
		int[] ycords = new int[len];
		for (int i=0; i<len; i++) {
			Point p = points[i];
			xcords[i] = p.getX();
			ycords[i] = p.getY();
		}
		
		g2d.fillPolygon(xcords, ycords, len);
		g2d.setColor(R);
		g2d.drawPolygon(xcords, ycords, len);
		g2d.setColor(c);
		
	}

}
