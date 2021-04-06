package ui.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import ui.HeuristicFunction;
import ui.SearchProblem;
import ui.SearchProblemAlgorithm;
import ui.SearchProblemResult;
import ui.State;
import ui.StateCostPair;

public class BreadthFirstSearch<S extends State> implements SearchProblemAlgorithm<S> {

	@Override
	public SearchProblemResult<S> executeAlgorithm(SearchProblem<S> problem, HeuristicFunction<? super S> h) {
		// heuristic function not used in BFS
		S initialState = problem.getInitialState();
		var succ = problem.getSuccessorFunction();
		var goal = problem.getGoalPredicate();
		
		if (initialState == null)
			return SearchProblemResult.fail();
		
		Function<SearchNode<S>, String> compKeyExtractor = n -> n.getState().toString();
		Comparator<SearchNode<S>> comp = Comparator.comparing(compKeyExtractor);
		Queue<SearchNode<S>> open = new LinkedList<>();
		SearchNode<S> initialNode = new SearchNode<>(initialState, 0, null);
		open.add(initialNode);
		
		Set<S> visited = new HashSet<>();
		
		while(!open.isEmpty()) {
			var n = open.remove();
			S sn = n.getState();
			if (goal.test(sn)) {
				// pronađeno ciljno stanje
				SearchProblemResult<S> result = new SearchProblemResult<>();
				result.solutionFound = true;
				result.totalCost = n.getCost();
				result.statesVisited = visited.size() + 1; //+1 jer brojimo i ciljno stanje
				
				LinkedList<StateCostPair<S>> solutionPath = new LinkedList<>();
				
				for (var itnode=n; itnode != null; itnode=itnode.getParent()) {
					solutionPath.addFirst(itnode);
				}
				
				result.solutionPath = solutionPath;
				result.pathLength = solutionPath.size();
				return result;
			}
			
			visited.add(sn);
			var expand = SearchNode.expand(n, succ);
			Collections.sort(expand, comp);
			for (var m : expand) {
				S sm = m.getState();
				if (!visited.contains(sm))
					open.add(m);
			}
		}
		return SearchProblemResult.fail();
	}

	@Override
	public String getAlgorithmName() {
		return "BFS";
	}
	
	
	
}
