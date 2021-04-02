package ui.algorithms;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import ui.HeuristicFunction;
import ui.SearchProblem;
import ui.SearchProblemAlgorithm;
import ui.SearchProblemResult;
import ui.State;

public class UniformCostSearch<S extends State> implements SearchProblemAlgorithm<S> {

	@Override
	public SearchProblemResult executeAlgorithm(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		// heuristic function not used in UCS
		S initialState = problem.getInitialState();
		var succ = problem.getSuccessorFunction();
		var goal = problem.getGoalPredicate();

		if (initialState == null)
			return SearchProblemResult.FAIL;
		
		Function<SearchNode<S>, String> strKeyExtractor = n -> n.getState().toString();
		Comparator<SearchNode<S>> compCosts = Comparator.comparingDouble(SearchNode::getCost);
		Comparator<SearchNode<S>> compStates = Comparator.comparing(strKeyExtractor);
		Comparator<SearchNode<S>> comp = compCosts.thenComparing(compStates);
		
		Queue<SearchNode<S>> open = new PriorityQueue<>(comp);
		SearchNode<S> initialNode = new SearchNode<>(initialState, 0, null);
		open.add(initialNode);

		Set<S> visited = new HashSet<>();

		while(!open.isEmpty()) {
			var n = open.remove();
			S sn = n.getState();
			if (goal.test(sn)) {
				// pronađeno ciljno stanje
				SearchProblemResult result = new SearchProblemResult();
				result.solutionFound = true;
				result.totalCost = n.getCost();
				result.statesVisited = visited.size() + 1;

				LinkedList<State> solutionPath = new LinkedList<>();

				for (var itnode=n; itnode != null; itnode=itnode.parent) {
					solutionPath.addFirst(itnode.getState());
				}

				result.solutionPath = solutionPath;
				result.pathLength = solutionPath.size();
				return result;
			}

			visited.add(sn);
			var expand = SearchNode.expand(n, succ);
			for (var m : expand) {
				S sm = m.getState();
				if (!visited.contains(sm))
					open.add(m);
			}
		}
		return SearchProblemResult.FAIL;
	}

	@Override
	public String getAlgorithmName() {
		return "UCS";
	}

}
