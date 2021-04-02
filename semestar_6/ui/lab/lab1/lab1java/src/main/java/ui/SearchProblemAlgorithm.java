package ui;

public interface SearchProblemAlgorithm<S extends State> {
	
	SearchProblemResult executeAlgorithm
			(SearchProblem<S> problem, HeuristicFunction<? super S> h);
	String getAlgorithmName();
	
}
