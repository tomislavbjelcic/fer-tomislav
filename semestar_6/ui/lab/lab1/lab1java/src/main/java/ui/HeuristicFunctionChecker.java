package ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

import ui.algorithms.UniformCostSearch;
import ui.collections.MySet;

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
		
		var rcmap = findAllTrueCosts(problem);
		Comparator<S> statecomp = Comparator.comparing(Object::toString);
		Map<S, Double> realCosts = new TreeMap<>(statecomp);
		realCosts.putAll(rcmap);
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
			MySet<StateCostPair<S>> successorSet = succ.apply(state);
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
	
	/*
	 * 
	 * Drugi pokušaj u kojem se prvo izvršava UCS za stanja sa najvećom heuristikom
	public static <S extends State> Map<S, Double> findAllTrueCostsV2
	(SearchProblem<S> problem, HeuristicFunction<S> h) {
		S initialState = problem.getInitialState();
		SearchProblemAlgorithm<S> alg = getTrueCostAlgo();
		Map<S, Double> trueCosts = new HashMap<>();
		Set<S> stateSet = problem.getStateSet();


		Map<S, Double> stateHeuristicMap = h.getAsMap();
		Function<Entry<S, Double>, S> keyExtractor = Entry::getKey;
		Comparator<S> stateComparator = Comparator.comparing(Object::toString);
		Function<Entry<S, Double>, Double> valExtractor = Entry::getValue;
		Comparator<Entry<S, Double>> compVal = Comparator.comparing(valExtractor);
		Comparator<Entry<S, Double>> compKey = Comparator.comparing(keyExtractor, stateComparator);
		Comparator<Entry<S, Double>> comp = compVal.thenComparing(compKey).reversed();

		Set<Entry<S, Double>> stateHeuristicEntries = new TreeSet<>(comp);
		stateHeuristicEntries.addAll(stateHeuristicMap.entrySet());

		System.out.println("State count: " + stateHeuristicEntries.size());

		int count = 0;
		int algexeccount = 0;
		for (var entry : stateHeuristicEntries) {
			S state = entry.getKey();
			count++;
			System.out.println("Processed " + count + ". Executed UCS " + algexeccount + " times.");

			if (trueCosts.containsKey(state))
				continue;
			problem.setInitialState(state);
			var results = alg.executeAlgorithm(problem, null);
			algexeccount++;
			//System.out.println("algexec");
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
	*/
	
	
	
}
