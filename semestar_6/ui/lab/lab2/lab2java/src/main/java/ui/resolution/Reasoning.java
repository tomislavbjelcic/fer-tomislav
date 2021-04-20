package ui.resolution;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ui.Clause;
import ui.ClauseSet;
import ui.Literal;
import ui.UnorderedPair;

public class Reasoning {
	
	private Reasoning() {}
	
	public static ProofResult prove(ClauseSet premises, Clause goal) {
		Objects.requireNonNull(premises);
		Objects.requireNonNull(goal);
		
		ClauseSet knowledge = premises.copy();
		Set<Literal> negGoal = goal.negate();
		
		Map<Clause, UnorderedPair<Clause>> proofMap = new HashMap<>();
		UnorderedPair<Clause> noparents = new UnorderedPair<>(null, null);
		for (Clause c : knowledge)
			proofMap.put(c, noparents);
		
		Deque<Clause> queue = new LinkedList<>();
		for (Literal l : negGoal) {
			Clause cl = Clause.fromLiterals(l);
			queue.add(cl);
		}
		
		while(!queue.isEmpty()) {
			Clause clause = queue.remove();
			for (Clause knowledgeClause : knowledge) {
				Clause resolveTry = clause.resolve(knowledgeClause);
				if (resolveTry == null || resolveTry.isTautology())
					continue;
				
				UnorderedPair<Clause> parents = new UnorderedPair<>(knowledgeClause, clause);
				
				if (resolveTry.isNIL()) {
					// rekonstruirati rjesenje
					return null;
				}
				
				
				proofMap.put(resolveTry, parents);
				queue.add(resolveTry);
				//IMA POSLA
				
			}
		}
		
		return null;
	}
	
	
}
