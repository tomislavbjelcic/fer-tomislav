package hr.fer.zemris.ooup.lab3.texteditor.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextEditorModel {
	
	private List<String> lines = new ArrayList<>();
	private LocationRange selectionRange;
	private Location cursorLocation;
	
	private List<CursorObserver> cursorObservers = new LinkedList<>();
	
	
	public TextEditorModel(String initialText) {
		Objects.requireNonNull(initialText);
		
		initialText.lines().forEach(lines::add);
		int len = initialText.length();
		if (len != 0 && initialText.charAt(len-1) == '\n')
			lines.add("");
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
		cursorObservers.remove(Objects.requireNonNull(co));
	}
	
	private void notifyCursorObservers(Location newLoc) {
		cursorObservers.forEach(obs -> obs.updateCursorLocation(newLoc));
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
		if (size == 0)
			return;
		
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
	
	
	@Override
	public String toString() {
		String lin = lines.stream().collect(Collectors.joining("\n"));
		return lin + '\n' + "Cursor: " + cursorLocation + '\n' + "Sel range: " + selectionRange;
	}
	
	private void test() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean cont = true;
		while(cont) {
			System.out.print("Key: ");
			String line = sc.nextLine();
			String strip = line.strip();
			if (strip.isEmpty())
				break;
			
			char first = strip.charAt(0);
			switch(first) {
			case 'w' -> moveCursorUp();
			case 's' -> moveCursorDown();
			case 'a' -> moveCursorLeft();
			case 'd' -> moveCursorRight();
			default -> cont=false;
			}
		}
		System.out.println("Test end.");
	}
	
	public static void main(String[] args) {
		String text = """
				GHUHH
				ui
				v
				ittu
				""";
		TextEditorModel m = new TextEditorModel(text);
		System.out.println(m.lines);
		System.out.println(m.lines.size());
		
		
		m.addCursorObserver(loc -> System.out.println("New location: " + loc));
		System.out.println(m);
		m.test();
		
	}
	
}

