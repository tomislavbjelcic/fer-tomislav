package ui;

public class Main {

	public static void main(String[] args) {
		/*
		AbstractLoader<Clause> loader = new ClauseLoader();

		String constant = "lab2_files/resolution_examples/";
		String pathstr = constant + args[0];
		Path p = Paths.get(pathstr);
		List<Clause> clauses = loader.loadFromFile(p);

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

		ProofResult res = Reasoning.prove(cs, last);
		System.out.println(res);
		*/

	}

}
