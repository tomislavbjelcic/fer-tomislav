package hr.fer.zemris.ooup.lab4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.ooup.lab4.GeometryUtil;

public abstract class AbstractGraphicalObject implements GraphicalObject {
	
	private Point[] hotPoints;
	private boolean[] hotPointSelected;
	private boolean selected = false;
	private List<GraphicalObjectListener> listeners = new ArrayList<>();
	
	public AbstractGraphicalObject(Point[] hotPoints) {
		this.hotPoints = hotPoints;
		hotPointSelected = new boolean[hotPoints.length];
	}
	
	@Override
	public Point getHotPoint(int index) {
		return hotPoints[index];
	}
	
	@Override
	public void setHotPoint(int index, Point point) {
		hotPoints[index] = point;
		notifyListeners();
	}
	
	@Override
	public int getNumberOfHotPoints() {
		return hotPoints.length;
	}
	
	@Override
	public double getHotPointDistance(int index, Point mousePoint) {
		Point hotPoint = getHotPoint(index);
		return GeometryUtil.distanceFromPoint(hotPoint, mousePoint);
	}
	
	@Override
	public boolean isHotPointSelected(int index) {
		return hotPointSelected[index];
	}
	
	@Override
	public void setHotPointSelected(int index, boolean selected) {
		hotPointSelected[index] = selected;
		notifyListeners();
	}
	
	@Override
	public boolean isSelected() {
		return selected;
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifySelectionListeners();
	}
	
	@Override
	public void translate(Point delta) {
		int hpcount = this.getNumberOfHotPoints();
		for (int i=0; i<hpcount; i++) {
			Point hp = this.getHotPoint(i);
			this.setHotPoint(i, hp.translate(delta));
		}
	}
	
	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.add(Objects.requireNonNull(l));
	}
	
	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}
	
	protected void notifyListeners() {
		listeners.forEach(l -> l.graphicalObjectChanged(this));
	}
	
	protected void notifySelectionListeners() {
		listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
	}
}
