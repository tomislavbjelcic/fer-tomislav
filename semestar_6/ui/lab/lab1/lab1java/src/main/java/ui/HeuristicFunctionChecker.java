package ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import ui.algorithms.SearchNode;

public class HeuristicFunctionChecker {
	
	private static final String CONDITION = "[CONDITION]";
	private static final String CONCLUSION = "[CONCLUSION]";
	private static final String OK = "[OK]";
	private static final String ERR = "[ERR]";
	private static final String HSTR = "h*";
	private static final String LEQ = "<=";
	private static final char SP = ' ';
	private static final char COLON = ':';
	private static final char C = 'c';
	private static final char PLUS = '+';
	private static final Function<State, String> HSTATESTR 
						= s -> "h(" + s.toString() + ")";
	
	private HeuristicFunctionChecker() {}
	
	public static <S extends State> void checkAndReportOptimistic
				(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		
		Map<S, Double> realCosts = findAllTrueCostsV2(problem);
		boolean optimistic = true;
		StringBuilder sb = new StringBuilder();
		
		// provjeriti sa autograderom: nesortirani ispis
		// koristiti mapu koju vraća findAllTrueCosts
		
		for (var entry : realCosts.entrySet()) {
			S state = entry.getKey();
			double realCost = entry.getValue();
			double heuristic = h.applyAsDouble(state);
			boolean cond = heuristic <= realCost;
			optimistic = optimistic && cond;
			String hstatestr = HSTATESTR.apply(state);
			// [CONDITION]: [OK] h(Opatija) <= h*: 26.0 <= 44.0
			sb.append(CONDITION).append(COLON).append(SP)
				.append(cond ? OK : ERR).append(SP)
				.append(hstatestr).append(SP).append(LEQ)
				.append(SP).append(HSTR).append(COLON)
				.append(SP).append(heuristic).append(SP)
				.append(LEQ).append(SP).append(realCost);
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		
		String addition = optimistic ? "" : "not ";
		String conclu = "Heuristic is " + addition + "optimistic.";
		sb.append(CONCLUSION).append(COLON).append(SP).append(conclu);
		System.out.println(sb.toString());
		
	}
	
	public static <S extends State> void checkAndReportConsistent
					(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		
		Set<S> stateSet = problem.getStateSet();
		// provjeriti treba li jos to zamotati u TreeSet
		SuccessorFunction<S> succ = problem.getSuccessorFunction();
		
		boolean consistent = true;
		StringBuilder sb = new StringBuilder();
		
		for (S state : stateSet) {
			Set<StateCostPair<S>> successorSet = succ.apply(state);
			// isto kao i prethodno
			for (var scpair : successorSet) {
				S to = scpair.getState();
				double cost = scpair.getCost();
				
				double hstate = h.applyAsDouble(state);
				double hto = h.applyAsDouble(to);
				
				boolean cond = hstate <= (hto + cost);
				consistent = consistent && cond;
				
				// ispis
				String hstatestr = HSTATESTR.apply(state);
				String htostr = HSTATESTR.apply(to);
				// [CONDITION]: [OK] h(Baderna) <= h(Višnjan) + c: 25.0 <= 20.0 + 13.0
				sb.append(CONDITION).append(COLON).append(SP)
				.append(cond ? OK : ERR).append(SP)
				.append(hstatestr).append(SP).append(LEQ)
				.append(SP).append(htostr).append(SP)
				.append(PLUS).append(SP).append(C)
				.append(COLON).append(SP).append(hstate)
				.append(SP).append(LEQ).append(SP)
				.append(hto).append(SP).append(PLUS)
				.append(SP).append(cost);
				
				System.out.println(sb.toString());
				sb.setLength(0);
				
			}
		}
		
		String addition = consistent ? "" : "not ";
		String conclu = "Heuristic is " + addition + "consistent.";
		sb.append(CONCLUSION).append(COLON).append(SP).append(conclu);
		System.out.println(sb.toString());
		
	}
	
	/*
	public static <S extends State> Map<S, Double> findAllTrueCosts
				(SearchProblem<S> problem) {
		S initialState = problem.getInitialState();
		SearchProblemAlgorithm<S> alg = getTrueCostAlgo();
		Map<S, Double> trueCosts = new HashMap<>();
		Set<S> stateSet = problem.getStateSet();
		
		for (S state : stateSet) {
			if (trueCosts.containsKey(state))
				continue;
			problem.setInitialState(state);
			var results = alg.executeAlgorithm(problem, null);
			
			if (!results.solutionFound) {
				trueCosts.put(state, Double.POSITIVE_INFINITY);
				continue;
			}
			
			var path = results.solutionPath;
			for (var pair : path) {
				S s = pair.getState();
				if (trueCosts.containsKey(s))
					break;
				double c =  results.totalCost - pair.getCost();
				trueCosts.put(s, c);
			}
		}
		
		problem.setInitialState(initialState);
		return trueCosts;
	}
	
	private static <S extends State> SearchProblemAlgorithm<S> getTrueCostAlgo() {
		return new UniformCostSearch<>();
	}
	*/
	
	public static <S extends State> Map<S, Double> findAllTrueCostsV2
								(SearchProblem<S> problem) {
		
		Set<S> states = problem.getStateSet();
		var succ = problem.getSuccessorFunction();
		
		// modeliraj obrnutu successor funkciju
		Map<S, Set<StateCostPair<S>>> reversedSucc = new HashMap<>();
		for (S state : states) {
			Set<StateCostPair<S>> set = new HashSet<>();
			reversedSucc.put(state, set);
		}
		for (S state : states) {
			var s = succ.apply(state);
			for (var p : s) {
				S from = state;
				S to = p.getState();
				double cost = p.getCost();
				StateCostPair<S> npair = new StateCostPair<>(from, cost);
				
				BiFunction<S, Set<StateCostPair<S>>, Set<StateCostPair<S>>> bf
						= (st, set) -> {
							set.add(npair);
							return set;
						};
				reversedSucc.compute(to, bf);
			}
		}
		
		SuccessorFunction<S> rsucc = new SuccessorFunction<S>() {

			@Override
			public Set<StateCostPair<S>> apply(S t) {
				return reversedSucc.get(t);
			}

			@Override
			public Set<S> getStateSet() {
				return null; //nece se koristiti
			}
			
		};
		
		
		// primijeni modificirani UCS za sva ciljna stanja
		Set<S> goalStates = problem.getGoalStates();
		Map<S, Double> realCosts = new HashMap<>();
		for (S state : states) {
			realCosts.put(state, Double.POSITIVE_INFINITY);
		}
		
		
		Function<SearchNode<S>, String> strKeyExtractor = n -> n.getState().toString();
		Comparator<SearchNode<S>> compCosts = Comparator.comparingDouble(SearchNode::getCost);
		Comparator<SearchNode<S>> compStates = Comparator.comparing(strKeyExtractor);
		Comparator<SearchNode<S>> comp = compCosts.thenComparing(compStates);
		
		Queue<SearchNode<S>> open = new PriorityQueue<>(comp);
		Set<S> visited = new HashSet<>();
		
		for (S gs : goalStates) {
			
			SearchNode<S> initialNode = new SearchNode<>(gs, 0, null);
			open.add(initialNode);

			
			
			while(!open.isEmpty()) {
				var n = open.remove();
				var sn = n.getState();
				visited.add(sn);
				
				double c1 = realCosts.get(sn).doubleValue();
				double c2 = n.getCost();
				if (Double.compare(c1, c2) > 0) {
					realCosts.remove(sn);
					realCosts.put(sn, c2);
				}
				
				var expand = SearchNode.expand(n, rsucc);
				for (var m : expand) {
					S sm = m.getState();
					if (!visited.contains(sm))
						open.add(m);
				}
			}
			
			open.clear();
			visited.clear();
		}
		
		return realCosts;
	}
	
	
	
}
