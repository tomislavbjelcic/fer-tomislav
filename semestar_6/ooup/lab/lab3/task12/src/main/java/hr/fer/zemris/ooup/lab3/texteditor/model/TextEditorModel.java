package hr.fer.zemris.ooup.lab3.texteditor.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextEditorModel {
	
	private final String LINE_SEPARATOR = System.lineSeparator();
	
	private List<String> lines = new ArrayList<>();
	private LocationRange selectionRange;
	private Location cursorLocation;
	
	private List<CursorObserver> cursorObservers = new LinkedList<>();
	private List<TextObserver> textObservers = new LinkedList<>();
	
	public TextEditorModel() {
		this("");
	}
	
	public TextEditorModel(String initialText) {
		Objects.requireNonNull(initialText);
		
		int len = initialText.length();
		
		if (len == 0)
			lines.add("");
		else
			initialText.lines().forEach(lines::add);
		
		if (initialText.endsWith(LINE_SEPARATOR))
			lines.add("");
		
		int size = lines.size();
		
		int rowIndex = size-1;
		int colIndex = lines.get(rowIndex).length();
		
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
	
	public String getLineSeparator() {
		return LINE_SEPARATOR;
	}
	
	private boolean validLoc(Location cursorLocation) {
		if (cursorLocation == null)
			return false;
		int lineCount = lines.size();
		if (lineCount == 0)
			return cursorLocation.rowIndex == 0 && cursorLocation.colIndex == 0;
		boolean validRow = cursorLocation.rowIndex < lineCount;
		if (!validRow)
			return false;
		String s = lines.get(cursorLocation.rowIndex);
		boolean validCol = cursorLocation.colIndex <= s.length();
		return validCol;
	}
	
	public void setCursorLocation(Location cursorLocation) {
		if (!validLoc(cursorLocation))
			return;
		boolean changed = !cursorLocation.equals(this.cursorLocation);
		if (changed) {
			this.cursorLocation = cursorLocation;
			notifyCursorObservers(cursorLocation);
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
		cursorObservers.remove(co);
	}
	
	private void notifyCursorObservers(Location newLoc) {
		cursorObservers.forEach(obs -> obs.updateCursorLocation(newLoc));
	}
	
	public void addTextObserver(TextObserver to) {
		textObservers.add(Objects.requireNonNull(to));
	}
	
	public void removeTextObserver(TextObserver to) {
		textObservers.remove(to);
	}
	
	private void notifyTextObservers() {
		textObservers.forEach(TextObserver::updateText);
	}
	
	public void moveCursorLeft() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		if (row == 0 && col == 0)
			return;
		
		if (col == 0) {
			int prevRow = row-1;
			String line = lines.get(prevRow);
			int len = line.length();
			Location loc = new Location(prevRow, len);
			this.setCursorLocation(loc);
			return;
		}
		
		Location loc = new Location(row, col-1);
		this.setCursorLocation(loc);
	}
	
	public void moveCursorRight() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		int size = lines.size();
		
		String currentLine = lines.get(row);
		if (col == currentLine.length()) {
			int nextRow = row+1;
			if (nextRow == size)
				return;
			
			Location loc = new Location(nextRow, 0);
			this.setCursorLocation(loc);
			return;
		}
		
		Location loc = new Location(row, col+1);
		this.setCursorLocation(loc);
	}
	
	public void moveCursorUp() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		
		if (row == 0)
			return;
		int prevRow = row-1;
		int prevLen = lines.get(prevRow).length();
		
		int newCol = prevLen >= col ? col : prevLen;
		Location loc = new Location(prevRow, newCol);
		this.setCursorLocation(loc);
		
	}
	
	public void moveCursorDown() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		
		int size = lines.size();
		if (row == size-1)
			return;
		
		int nextRow = row+1;
		int nextLen = lines.get(nextRow).length();
		
		int newCol = nextLen >= col ? col : nextLen;
		Location loc = new Location(nextRow, newCol);
		this.setCursorLocation(loc);
	}
	
	public void insert(char c) {
		insert(String.valueOf(c));
	}
	
	public void insert(String text) {
		List<String> textLines = text.lines().collect(Collectors.toCollection(LinkedList::new));
		if (text.endsWith(LINE_SEPARATOR))
			textLines.add("");
		
		int size = textLines.size();
		if (size == 0)
			return;
		
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		
		Location newCursorLoc = null;
		String currentLine = lines.get(row);
		if (size == 1) {
			StringBuilder mutable = new StringBuilder(currentLine);
			String ins = textLines.get(0);
			mutable.insert(col, ins);
			
			lines.set(row, mutable.toString());
			newCursorLoc = new Location(row, col+ins.length());
		} else {
			String leftSide = currentLine.substring(0, col);
			String rightSide = currentLine.substring(col);
			
			String first = textLines.get(0);
			String last = textLines.get(size-1);
			textLines.set(0, leftSide.concat(first));
			textLines.set(size-1, last.concat(rightSide));
			
			lines.remove(row);
			lines.addAll(row, textLines);
			
			int newRow = row-1+size;
			int newCol = last.length();
			newCursorLoc = new Location(newRow, newCol);
		}
		
		this.notifyTextObservers();
		this.setCursorLocation(newCursorLoc);
	}
	
	public void deleteAfter() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		int size = lines.size();
		
		String currStr = lines.get(row);
		int strLen = currStr.length();
		if (col == strLen) {
			if (row == size-1)
				return;
			
			// u ovom slucaju treba spojiti dva retka jer se brise prijelaz u novu liniju
			String nextLine = lines.get(row+1);
			String merged = currStr.concat(nextLine);
			
			lines.set(row, merged);
			lines.remove(row+1);
			
		} else {
			StringBuilder mutable = new StringBuilder(currStr);
			mutable.deleteCharAt(col);
			String removed = mutable.toString();
			lines.set(row, removed);
		}
		
		this.notifyTextObservers();
	}
	
	public void deleteBefore() {
		int row = cursorLocation.rowIndex;
		int col = cursorLocation.colIndex;
		
		if (row == 0 && col == 0)
			return;
		
		String currStr = lines.get(row);
		Location newCursorLoc = null;
		if (col == 0) {
			// opet spojiti dva retka
			
			String prevLine = lines.get(row-1);
			String merged = prevLine.concat(currStr);
			
			lines.set(row-1, merged);
			lines.remove(row);
			newCursorLoc = new Location(row-1, prevLine.length());
		} else {
			StringBuilder mutable = new StringBuilder(currStr);
			mutable.deleteCharAt(col-1);
			String removed = mutable.toString();
			lines.set(row, removed);
			newCursorLoc = new Location(row, col-1);
		}
		
		this.notifyTextObservers();
		this.setCursorLocation(newCursorLoc);
	}
	
	
	@Override
	public String toString() {
		String lin = lines.stream().collect(Collectors.joining("\n"));
		return lin + '\n' + "Cursor: " + cursorLocation + '\n' + "Sel range: " + selectionRange;
	}
	
}

