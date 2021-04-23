package ui.resolution;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import ui.Clause;
import ui.UnorderedPair;

public class ProofResult {
	
	private static final String SEP = "===============";
	private static final String CONCLU = "[CONCLUSION]";
	private static final Function<Map.Entry<Clause, UnorderedPair<Integer>>, String> ENTRY_STR_F = e -> {
		var parents = e.getValue();
		var clause = e.getKey();
		boolean hasParents = parents.getFirst() != null;
		String st = hasParents ? " " + parents.toString() : "";
		return clause.toString() + st;
	};
	private static final char NL = '\n';
	private static final char DOT = '.';
	private static final char COLON = ':';
	private static final char SP = ' ';
	
	public boolean success = false;
	public Map<Clause, UnorderedPair<Integer>> usedClauses = null;
	public Clause goal = null;
	
	public static ProofResult fail(Clause goal) {
		Objects.requireNonNull(goal);
		ProofResult pr = new ProofResult();
		pr.goal = goal;
		return pr;
	}
	
	@Override
	public String toString() {
		String concluStr = CONCLU + COLON + SP + goal.toString() + " is " + (success ? "true" : "unknown");
		if (!success)
			return concluStr;
		
		StringBuilder sb = new StringBuilder();
		int i=1;
		boolean premisesPassed = false;
		for (var e : usedClauses.entrySet()) {
			Integer prnt = e.getValue().getFirst();
			if (!premisesPassed && prnt!=null) {
				premisesPassed = true;
				sb.append(SEP).append(NL);
			}
			
			sb.append(i++).append(DOT).append(SP).append(ENTRY_STR_F.apply(e)).append(NL);
		}
		
		sb.append(SEP).append(NL);
		sb.append(concluStr);
		return sb.toString();
	}
	
}
