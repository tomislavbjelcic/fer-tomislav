package ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ui.loaders.ClauseLoader;

public class Solution {

	public static void main(String ... args) {
		
		String constant = "lab2_files/resolution_examples/";
		String pathstr = constant + args[0];
		Path p = Paths.get(pathstr);
		List<Clause> clauses = ClauseLoader.loadFromFile(p);
		
		ClauseSet cs = new ClauseSet();
		Clause last = null;
		int size = clauses.size();
		
		var lit = clauses.listIterator();
		while (lit.hasNext()) {
			int idx = lit.nextIndex();
			Clause c = lit.next();
			if (idx == size - 1)
				last = c;
			else
				cs.addClause(c);
		}
		
		System.out.println("Goal clause: " + last);
		System.out.println("Premises:");
		for (Clause c : cs)
			System.out.println(c);
		
		System.out.println();
		System.out.println(cs);
		
		
	}

}
