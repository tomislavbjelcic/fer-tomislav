package ui;

import java.util.List;

public class SearchProblemResult {
	
	public static final SearchProblemResult FAIL = new SearchProblemResult();
	
	public boolean solutionFound = false;
	public int statesVisited = 0;
	public int pathLength = 0;
	public double totalCost = 0.0;
	public List<State> solutionPath = null;
	
}
