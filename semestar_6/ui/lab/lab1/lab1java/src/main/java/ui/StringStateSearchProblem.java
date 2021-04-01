package ui;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class StringStateSearchProblem implements SearchProblem<StringState> {
	
	private StringState initialState;
	private Set<StringState> goalStates;
	private StringStateSuccessorFunction succ;
	
	public StringStateSearchProblem(StringState initialState, StringStateSuccessorFunction succ, StringState... goalStates) {
		check(initialState, succ, goalStates);
		this.initialState = initialState;
		this.succ = succ;
		this.goalStates = new HashSet<>();
		for (var s : goalStates)
			this.goalStates.add(s);
	}
	
	private static void check(StringState initialState, StringStateSuccessorFunction succ, StringState... goalStates) {
		var e = new IllegalArgumentException("Problem not well defined.");
		
		if (initialState == null || succ == null || goalStates == null || goalStates.length < 1)
			throw e; 
	}
	
	@Override
	public StringState getInitialState() {
		return initialState;
	}

	@Override
	public SuccessorFunction<StringState> getSuccessorFunction() {
		return succ;
	}

	@Override
	public Predicate<StringState> getGoalPredicate() {
		return goalStates::contains;
	}

}
