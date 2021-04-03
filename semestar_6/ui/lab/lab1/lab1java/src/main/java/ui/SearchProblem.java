package ui;

import java.util.Set;
import java.util.function.Predicate;

public interface SearchProblem<S extends State> {
	
	S getInitialState();
	void setInitialState(S state);
	SuccessorFunction<S> getSuccessorFunction();
	Predicate<S> getGoalPredicate();
	default Set<S> getStateSet() {
		return getSuccessorFunction().getStateSet();
	}
	
}
