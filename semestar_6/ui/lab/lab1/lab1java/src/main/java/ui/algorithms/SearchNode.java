package ui.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ui.State;
import ui.StateCostPair;
import ui.SuccessorFunction;

class SearchNode<S extends State> extends StateCostPair<S> {
	
	SearchNode<S> parent;
	
	SearchNode(S state, double cost, SearchNode<S> parent) {
		super(state, cost);
		this.parent = parent;
	}
	
	SearchNode<S> getParent() {
		return parent;
	}
	
	static <T extends State> List<SearchNode<T>> expand(SearchNode<T> parent, SuccessorFunction<T> succ) {
		List<SearchNode<T>> expanded = new ArrayList<>(); // da se brže sortira kod BFS-a
		T ps = parent.getState();
		Set<StateCostPair<T>> successors = succ.apply(ps);
		for (var s : successors) {
			T state = s.getState();
			double costTotal = parent.getCost() + s.getCost();
			SearchNode<T> child = new SearchNode<>(state, costTotal, parent);
			
			expanded.add(child);
		}
		return expanded;
	}
	
	
	
}
