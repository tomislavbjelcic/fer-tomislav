package hr.fer.zemris.ooup.lab4.model;

public class AddShapeState extends IdleState {
	
	private GraphicalObject prototype;
	private DocumentModel model;
	
	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		this.model = model;
		this.prototype = prototype;
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject go = prototype.duplicate();
		int hpcount = go.getNumberOfHotPoints();
		for (int i=0; i<hpcount; i++) {
			Point hp = go.getHotPoint(i);
			go.setHotPoint(i, hp.translate(mousePoint));
		}
		
		model.addGraphicalObject(go);
		
	}
	
}
