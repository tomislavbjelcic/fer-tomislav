package ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import ui.algorithms.UniformCostSearch;

public class HeuristicFunctionChecker {
	
	private static final String CONDITION = "[CONDITION]";
	private static final String CONCLUSION = "[CONCLUSION]";
	private static final String OK = "[OK]";
	private static final String ERR = "[ERR]";
	private static final String HSTR = "h*";
	private static final String LEQ = "<=";
	private static final char SP = ' ';
	private static final char COLON = ':';
	
	private HeuristicFunctionChecker() {}
	
	public static <S extends State> void checkAndReportOptimistic
				(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		
		var rcmap = findAllTrueCosts(problem);
		Comparator<S> statecomp = Comparator.comparing(Object::toString);
		SortedMap<S, Double> realCosts = new TreeMap<>(statecomp);
		realCosts.putAll(rcmap);
		boolean optimistic = true;
		StringBuilder sb = new StringBuilder();
		
		for (var entry : realCosts.entrySet()) {
			S state = entry.getKey();
			double realCost = entry.getValue();
			double heuristic = h.applyAsDouble(state);
			boolean cond = heuristic <= realCost;
			optimistic = optimistic && cond;
			String hstatestr = "h(" + state.toString() + ")";
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
	
	private static <S extends State> SearchProblemAlgorithm<S> getTrueCostAlgo() {
		return new UniformCostSearch<>();
	}
	
	
	
}
