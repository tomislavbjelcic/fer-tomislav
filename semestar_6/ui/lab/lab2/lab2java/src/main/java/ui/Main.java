package ui;

import java.util.HashMap;
import java.util.Map;

public class Main {
	
	public static void main(String[] args) {
		Map<Integer, Integer> m = new HashMap<>();
		m.put(1, 7);
		m.put(-2, 5);
		m.put(3, 7);
		
		
		var s = m.keySet();
		s.add(9);
		
		System.out.println("Map: " + m);
		System.out.println("Set view: " + s);
		
	}
	
}
