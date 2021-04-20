package ui;

import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		List<Integer> l = List.of(4, 5, -2, 0, 4);
		
		var lit = l.listIterator();
		while(lit.hasNext()) {
			int idx = lit.nextIndex();
			int next = lit.next();
			System.out.println("Element at index " + idx + ": " + next);
		}
		
	}
	
}
