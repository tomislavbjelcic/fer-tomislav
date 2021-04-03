package ui;

import java.util.List;
import java.util.stream.Collectors;

public class SearchProblemResult<S extends State> {
	
	
	public boolean solutionFound = false;
	public int statesVisited = 0;
	public int pathLength = 0;
	public double totalCost = 0.0;
	public List<StateCostPair<S>> solutionPath = null;
	
	public static <T extends State> SearchProblemResult<T> fail() {
		return new SearchProblemResult<>();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[FOUND_SOLUTION]: " + (solutionFound ? "yes" : "no"));
		if (!solutionFound)
			return sb.toString();
		char nl = '\n';
		String pathStr = solutionPath == null ? "" :
			solutionPath.stream().map(StateCostPair::getState)
						.map(Object::toString)
						.collect(Collectors.joining(" => "));
		sb.append(nl).append("[STATES_VISITED]: ").append(statesVisited).append(nl)
				.append("[PATH_LENGTH]: ").append(pathLength).append(nl)
				.append("[TOTAL_COST]: ").append(totalCost).append(nl)
				.append("[PATH]: ").append(pathStr);
		return sb.toString();
	}
	
}
