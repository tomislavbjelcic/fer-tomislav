package ui;

import java.util.Set;
import java.util.function.Predicate;

public interface SearchProblem<S extends State> {
	
	S getInitialState();
	void setInitialState(S state);
	SuccessorFunction<S> getSuccessorFunction();
	Set<S> getGoalStates();
	default Predicate<S> getGoalPredicate() {
		Set<S> goalStates = getGoalStates();
		return goalStates::contains;
	}
	default Set<S> getStateSet() {
		return getSuccessorFunction().getStateSet();
	}
	
}
