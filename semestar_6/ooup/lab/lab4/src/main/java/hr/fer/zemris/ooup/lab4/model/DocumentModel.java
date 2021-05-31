package hr.fer.zemris.ooup.lab4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DocumentModel {
	
	public final static double SELECTION_PROXIMITY = 10;

	// Kolekcija svih grafičkih objekata:
	private List<GraphicalObject> objects = new ArrayList<>();
	// Read-Only proxy oko kolekcije grafičkih objekata:
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	// Kolekcija prijavljenih promatrača:
	private List<DocumentModelListener> listeners = new ArrayList<>();
	// Kolekcija selektiranih objekata:
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	// Read-Only proxy oko kolekcije selektiranih objekata:
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

	// Promatrač koji će biti registriran nad svim objektima crteža...
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

		@Override
		public void graphicalObjectChanged(GraphicalObject go) {
			notifyListeners();
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {
			int idx = selectedObjects.indexOf(go);
			boolean sel = go.isSelected();
			if (idx == -1 && sel)
				selectedObjects.add(go);
			if (idx != -1 && !sel) {
				selectedObjects.remove(idx);
			}
			notifyListeners();
		}
		
	};
	
	// Konstruktor...
	public DocumentModel() {
		
	}

	// Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
	// i potom obavijeste svi promatrači modela
	public void clear() {
		objects.stream().forEach(go -> go.removeGraphicalObjectListener(goListener));
		objects.clear();
		selectedObjects.clear();
		notifyListeners();
	}

	// Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte
	// model kao promatrača)
	public void addGraphicalObject(GraphicalObject obj) {
		boolean sel = obj.isSelected();
		obj.addGraphicalObjectListener(goListener);
		objects.add(obj);
		if (sel)
			selectedObjects.add(obj);
		notifyListeners();
	}

	// Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte
	// model kao promatrača)
	public void removeGraphicalObject(GraphicalObject obj) {
		boolean sel = obj.isSelected();
		obj.removeGraphicalObjectListener(goListener);
		objects.remove(obj);
		if (sel)
			selectedObjects.remove(obj);
		notifyListeners();
	}

	// Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz
	// metode modela)
	public List<GraphicalObject> list() {
		return roObjects;
	}

	// Prijava...
	public void addDocumentModelListener(DocumentModelListener l) {
		listeners.add(l);
	}

	// Odjava...
	public void removeDocumentModelListener(DocumentModelListener l) {
		listeners.remove(l);
	}

	// Obavještavanje...
	public void notifyListeners() {
		listeners.stream().forEach(DocumentModelListener::documentChange);
	}

	// Vrati nepromjenjivu listu selektiranih objekata
	public List<GraphicalObject> getSelectedObjects() {
		return roSelectedObjects;
	}

	// Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
	// Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
	public void increaseZ(GraphicalObject go) {
		
	}

	// Pomakni predani objekt u listi objekata na jedno mjesto ranije...
	public void decreaseZ(GraphicalObject go) {
		
	}

	// Pronađi postoji li u modelu neki objekt koji klik na točku koja je
	// predana kao argument selektira i vrati ga ili vrati null. Točka selektira
	// objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
	// SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		Comparator<GraphicalObject> goComp = Comparator.comparingDouble(g -> g.selectionDistance(mousePoint));
		Optional<GraphicalObject> ogo = objects.stream()
				.filter(go -> go.selectionDistance(mousePoint) <= SELECTION_PROXIMITY)
				.min(goComp);
		
		return ogo.isPresent() ? ogo.get() : null;
		
	}

	// Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
	// Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
	// udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa
	// kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
	// se pri tome NE dira.
	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		int hpcount = object.getNumberOfHotPoints();
		
		double min = 0.0;
		int idx = -1;
		for (int i=0; i<hpcount; i++) {
			double prox = object.getHotPointDistance(i, mousePoint);
			
			if (prox < SELECTION_PROXIMITY && (idx==-1 || prox<min)) {
				idx = i;
				min = prox;
			}
		}
		return idx;
	}

}
