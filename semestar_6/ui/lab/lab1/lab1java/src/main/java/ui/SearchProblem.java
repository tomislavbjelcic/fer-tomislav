package ui;

import java.util.function.Predicate;

public interface SearchProblem<S extends State> {
	
	S getInitialState();
	SuccessorFunction<S> getSuccessorFunction();
	Predicate<S> getGoalPredicate();
	
}
