package ui.resolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

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
		Set<Clause> negGoalClauses = new HashSet<>();
		for (Literal l : negGoal) {
			Clause cl = Clause.fromLiterals(l);
			negGoalClauses.add(cl);
		}
		
		Map<Clause, UnorderedPair<Clause>> proofMap = new HashMap<>();
		UnorderedPair<Clause> noparents = new UnorderedPair<>(null, null);
		for (Clause c : knowledge)
			proofMap.put(c, noparents);
		
		Deque<Clause> queue = new LinkedList<>(negGoalClauses);
		
		
		while(!queue.isEmpty()) {
			Clause clause = queue.remove();
			for (Clause knowledgeClause : knowledge) {
				Clause resolveTry = clause.resolve(knowledgeClause);
				if (resolveTry == null || resolveTry.isTautology())
					continue;
				
				UnorderedPair<Clause> parents = new UnorderedPair<>(knowledgeClause, clause);
				proofMap.put(resolveTry, parents);
				
				if (resolveTry.isNIL()) {
					// rekonstruirati rjesenje
					ProofResult result = new ProofResult();
					Map<Clause, Integer> used = new HashMap<>();
					Integer dummy = Integer.valueOf(0);
					Deque<Clause> q = new LinkedList<>();
					q.add(resolveTry);
					
					while(!q.isEmpty()) {
						Clause top = q.remove();
						used.put(top, dummy);
						
						var pair = proofMap.get(top);
						Clause p1 = pair.getFirst();
						Clause p2 = pair.getSecond();
						if (p1==null || p2==null)
							continue;
						if (!used.containsKey(p1))
							q.add(p1);
						if (!used.containsKey(p2))
							q.add(p2);
					}
					
					Function<UnorderedPair<Clause>, Clause> extractor = UnorderedPair::getFirst;
					Comparator<Clause> clausecomp = (c1, c2) -> {
						if (c1==null & c2==null)
							return 0;
						if (c1==null)
							return -1;
						return 1;
					};
					Comparator<UnorderedPair<Clause>> cpcomp = Comparator.comparing(extractor, clausecomp);
					Comparator<Clause> comp = (c1, c2) -> {
						var pair1 = proofMap.get(c1);
						var pair2 = proofMap.get(c2);
						return cpcomp.compare(pair1, pair2);
					};
					Function<Clause, Boolean> isNegGoal = negGoalClauses::contains;
					Comparator<Clause> compCheckGoal = Comparator.comparing(isNegGoal);
					Comparator<Clause> finalComp = comp.thenComparing(compCheckGoal);
					
					List<Clause> list = new ArrayList<>(used.keySet());
					list.sort(finalComp);
					int size = list.size();
					for (int i=0; i<size; i++) {
						used.put(list.get(i), i+1);
					}
					
					Map<Clause, UnorderedPair<Integer>> resultMap = new LinkedHashMap<>();
					for (Clause cls : used.keySet()) {
						UnorderedPair<Clause> clpair = proofMap.get(cls);
						Integer i1 = used.get(clpair.getFirst());
						Integer i2 = used.get(clpair.getSecond());
						UnorderedPair<Integer> ipair = new UnorderedPair<>(i1, i2);
						resultMap.put(cls, ipair);
					}
					
					result.success = true;
					result.usedClauses = resultMap;
					result.goal = goal;
					return result;
				}
				
				
				queue.add(resolveTry);
				
			}
			
			knowledge.addClause(clause);
		}
		
		return ProofResult.FAIL;
	}
	
	
}
