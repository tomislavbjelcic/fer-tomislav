package ui;

import java.util.List;
import java.util.stream.Collectors;

public class SearchProblemResult {
	
	public static final SearchProblemResult FAIL = new SearchProblemResult();
	
	public boolean solutionFound = false;
	public int statesVisited = 0;
	public int pathLength = 0;
	public double totalCost = 0.0;
	public List<State> solutionPath = null;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[FOUND_SOLUTION]: " + (solutionFound ? "yes" : "no"));
		if (!solutionFound)
			return sb.toString();
		char nl = '\n';
		String pathStr = solutionPath == null ? "" :
			solutionPath.stream().map(Object::toString)
						.collect(Collectors.joining(" => "));
		sb.append(nl).append("[STATES_VISITED]: ").append(statesVisited).append(nl)
				.append("[PATH_LENGTH]: ").append(pathLength).append(nl)
				.append("[TOTAL_COST]: ").append(totalCost).append(nl)
				.append("[PATH]: ").append(pathStr);
		return sb.toString();
	}
	
}
