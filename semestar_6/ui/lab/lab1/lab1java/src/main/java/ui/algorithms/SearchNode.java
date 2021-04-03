package ui.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ui.HeuristicFunction;
import ui.State;
import ui.StateCostPair;
import ui.SuccessorFunction;

class SearchNode<S extends State> extends StateCostPair<S> {
	
	private double costWithHeuristic;
	private SearchNode<S> parent;
	
	SearchNode(S state, double cost, SearchNode<S> parent) {
		this(state, cost, 0.0, parent);
	}
	
	SearchNode(S state, double cost, double h, SearchNode<S> parent) {
		super(state, cost);
		this.costWithHeuristic = cost + h;
		this.parent = parent;
	}
	
	SearchNode<S> getParent() {
		return parent;
	}
	
	double getCostWithHeuristic() {
		return costWithHeuristic;
	}
	
	static <T extends State> List<SearchNode<T>> expand
		(SearchNode<T> parent, SuccessorFunction<T> succ) {
		return expand(parent, succ, HeuristicFunction.getDefault());
	}
	
	static <T extends State> List<SearchNode<T>> expand
			(SearchNode<T> parent, SuccessorFunction<T> succ, HeuristicFunction<? super T> h) {
		List<SearchNode<T>> expanded = new ArrayList<>(); // da se brže sortira kod BFS-a
		T ps = parent.getState();
		Set<StateCostPair<T>> successors = succ.apply(ps);
		for (var s : successors) {
			T state = s.getState();
			double costTotal = parent.getCost() + s.getCost();
			double stateHeuristic = h.applyAsDouble(state);
			SearchNode<T> child = new SearchNode<>(state, costTotal, stateHeuristic, parent);
			
			expanded.add(child);
		}
		return expanded;
	}
	
	
	
}
