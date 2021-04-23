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
		
		Map<Clause, UnorderedPair<Clause>> proofMap = new HashMap<>();
		UnorderedPair<Clause> noparents = new UnorderedPair<>(null, null);
		
		for (Literal l : negGoal) {
			Clause cl = Clause.fromLiterals(l);
			negGoalClauses.add(cl);
			proofMap.put(cl, noparents);
		}
		
		
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
					
					Deque<Clause> q = new LinkedList<>();
					q.add(resolveTry);
					
					int order=1;
					while(!q.isEmpty()) {
						Clause top = q.remove();
						used.put(top, order++);
						
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
					
					Comparator<Clause> alwaysEq = (c1, c2) -> 0;
					Comparator<Clause> nullFirst = Comparator.nullsFirst(alwaysEq);
					
					Function<Clause, UnorderedPair<Clause>> parentExtractor = proofMap::get;
					Function<UnorderedPair<Clause>, Clause> firstParentExtractor = UnorderedPair::getFirst;
					Function<Clause, Clause> pExtr = parentExtractor.andThen(firstParentExtractor);
					Comparator<Clause> nullParentsFirst = Comparator.comparing(pExtr, nullFirst);
					
					Function<Clause, Integer> orderExtractor = used::get;
					Comparator<Integer> ci = Comparator.reverseOrder();
					Comparator<Clause> orderComparator = Comparator.comparing(orderExtractor, ci);
					
					Function<Clause, Boolean> isNegGoal = negGoalClauses::contains;
					Comparator<Clause> compCheckGoal = Comparator.comparing(isNegGoal);
					
					Comparator<Clause> finalComp = nullParentsFirst.thenComparing(compCheckGoal).thenComparing(orderComparator);
					
					List<Clause> list = new ArrayList<>(used.keySet());
					list.sort(finalComp);
					int size = list.size();
					for (int i=0; i<size; i++) {
						used.put(list.get(i), i+1);
					}
					
					Map<Clause, UnorderedPair<Integer>> resultMap = new LinkedHashMap<>();
					for (Clause cls : list) {
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
		
		return ProofResult.fail(goal);
	}
	
	
}
