package hr.fer.zemris.ooup.lab3.texteditor.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class TextEditorModel {
	
	private List<String> lines = new ArrayList<>();
	private LocationRange selectionRange;
	private Location cursorLocation;
	
	private List<CursorObserver> cursorObservers = new LinkedList<>();
	private Consumer<CursorObserver> notifier = obs -> obs.updateCursorLocation(cursorLocation);
	
	
	public TextEditorModel(String initialText) {
		Objects.requireNonNull(initialText);
		
		initialText.lines().forEach(lines::add);
		int size = lines.size();
		
		boolean empty = size == 0;
		int rowIndex = empty ? 0 : size-1;
		int colIndex = empty ? 0 : lines.get(size-1).length();
		
		this.setCursorLocation(new Location(rowIndex, colIndex));
		this.setSelectionRange(new LocationRange(new Location(), new Location()));
	}
	
	public LocationRange getSelectionRange() {
		return selectionRange;
	}
	
	public void setSelectionRange(LocationRange selectionRange) {
		this.selectionRange = selectionRange;
	}

	public Location getCursorLocation() {
		return cursorLocation;
	}

	public void setCursorLocation(Location cursorLocation) {
		boolean changed = !this.cursorLocation.equals(cursorLocation);
		if (changed) {
			this.cursorLocation = cursorLocation;
			notifyCursorObservers();
		}
	}
	
	private static class RangeIterator implements Iterator<String> {
		
		ListIterator<String> it;
		int currentIndex;
		int index2;
		
		RangeIterator(List<String> list, int index1, int index2) {
			it = list.listIterator(index1);
			this.currentIndex = index1;
			this.index2 = index2;
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext() && currentIndex<index2;
		}

		@Override
		public String next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			String n = it.next();
			currentIndex++;
			return n;
		}
		
	}
	
	public Iterator<String> allLines() {
		return new RangeIterator(lines, 0, lines.size());
	}
	
	public Iterator<String> linesRange(int index1, int index2) {
		return new RangeIterator(lines, index1, index2);
	}
	
	
	
	public void addCursorObserver(CursorObserver co) {
		cursorObservers.add(Objects.requireNonNull(co));
	}
	
	public void removeCursorObserver(CursorObserver co) {
		cursorObservers.remove(Objects.requireNonNull(co));
	}
	
	private void notifyCursorObservers() {
		cursorObservers.forEach(notifier);
	}
	
	public void moveCursorLeft() {}
	public void moveCursorRight() {}
	public void moveCursorUp() {}
	public void moveCursorDown() {}
	
	public static void main(String[] args) {
		String text = "ola\nzuizy\r\nlr";
		TextEditorModel m = new TextEditorModel(text);
		
		var it = m.linesRange(0, 2);
		while(it.hasNext()) {
			String n = it.next();
			System.out.println(n);
		}
	}
	
}

