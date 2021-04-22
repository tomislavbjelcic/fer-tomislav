package ui.resolution;

import java.util.Map;

import ui.Clause;
import ui.UnorderedPair;

public class ProofResult {
	
	public static final ProofResult FAIL = new ProofResult();
	
	public boolean success = false;
	public Map<Clause, UnorderedPair<Integer>> usedClauses = null;
	public Clause goal = null;
	
	@Override
	public String toString() {
		if (!success)
			return goal.toString() + "is unknown";
		
		StringBuilder sb = new StringBuilder();
		int i=1;
		for (var e : usedClauses.entrySet()) {
			String s = "" + i + ". " + e.getKey() + " " + e.getValue() + "\n";
			sb.append(s);
		}
		return sb.toString();
	}
	
}
