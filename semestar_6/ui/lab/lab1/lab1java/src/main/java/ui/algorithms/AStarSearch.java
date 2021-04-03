package ui.algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import ui.HeuristicFunction;
import ui.SearchProblem;
import ui.SearchProblemAlgorithm;
import ui.SearchProblemResult;
import ui.State;

public class AStarSearch<S extends State> implements SearchProblemAlgorithm<S> {

	@Override
	public SearchProblemResult executeAlgorithm(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		S initialState = problem.getInitialState();
		var succ = problem.getSuccessorFunction();
		var goal = problem.getGoalPredicate();

		if (initialState == null)
			return SearchProblemResult.FAIL;
		
		Function<SearchNode<S>, String> strKeyExtractor = n -> n.getState().toString();
		Comparator<SearchNode<S>> compCostHeuristic = Comparator.comparingDouble(SearchNode::getCostWithHeuristic);
		Comparator<SearchNode<S>> compStates = Comparator.comparing(strKeyExtractor);
		Comparator<SearchNode<S>> comp = compCostHeuristic.thenComparing(compStates);
		
		SortedMap<SearchNode<S>, SearchNode<S>> open = new TreeMap<>(comp);
		SearchNode<S> initialNode = new SearchNode<>(initialState, 0, null);
		open.put(initialNode, initialNode);

		Map<SearchNode<S>, SearchNode<S>> visited = new HashMap<>();
		
		while(!open.isEmpty()) {
			var n = open.firstKey();
			open.remove(n);
			S sn = n.getState();
			if (goal.test(sn)) {
				// pronađeno ciljno stanje
				SearchProblemResult result = new SearchProblemResult();
				result.solutionFound = true;
				result.totalCost = n.getCost();
				result.statesVisited = visited.size() + 1;

				LinkedList<State> solutionPath = new LinkedList<>();

				for (var itnode=n; itnode != null; itnode=itnode.getParent()) {
					solutionPath.addFirst(itnode.getState());
				}

				result.solutionPath = solutionPath;
				result.pathLength = solutionPath.size();
				return result;
			}

			visited.put(n, n);
			
			var expand = SearchNode.expand(n, succ, h);
			for (var m : expand) {
				
				// provjeri visited
				Map<SearchNode<S>, SearchNode<S>> checking = visited;
				SearchNode<S> m_ = checking.get(m);
				if (m_ == null) {
					// ako nema u visitedu, provjeri open
					checking = open;
					m_ = checking.get(m);
				}
				
				if (m_ != null) {
					if (m_.getCost() < m.getCost())
						continue;
					checking.remove(m_);
				}
				
				open.put(m, m);
				
				

			}
		}
		return SearchProblemResult.FAIL;
	}

	@Override
	public String getAlgorithmName() {
		return "A-STAR";
	}

}
