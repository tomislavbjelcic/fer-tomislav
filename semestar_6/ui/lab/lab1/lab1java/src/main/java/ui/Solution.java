package ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import ui.algorithms.AStarSearch;
import ui.algorithms.BreadthFirstSearch;
import ui.algorithms.UniformCostSearch;
import ui.args.Arguments;
import ui.loaders.StringStateHeuristicFunctionLoader;
import ui.loaders.StringStateSearchProblemLoader;

public class Solution {
	
	private static final Map<String, SearchProblemAlgorithm<StringState>> ALGO_MAP = getAlgoMap();
	
	private static Map<String, SearchProblemAlgorithm<StringState>> getAlgoMap() {
		Map<String, SearchProblemAlgorithm<StringState>> m = new HashMap<>();
		m.put("bfs", new BreadthFirstSearch<>());
		m.put("ucs", new UniformCostSearch<>());
		m.put("astar", new AStarSearch<>());
		return m;
	}

	public static void main(String ... args) {
		
		Arguments arguments = Arguments.parse(args);
		
		String sspathStr = arguments.getArgSS();
		String algStr = arguments.getArgAlg();
		String hpathStr = arguments.getArgH();
		boolean consistencyCheck = arguments.checkConsistentFlag();
		boolean optimisticCheck = arguments.checkOptimisticFlag();
		
		Path sspath = Paths.get(sspathStr);
		SearchProblem<StringState> problem = StringStateSearchProblemLoader.loadProblemFromFile(sspath);
		
		
		HeuristicFunction<StringState> h = null;
		if (hpathStr != null) {
			Path hpath = Paths.get(hpathStr);
			h = StringStateHeuristicFunctionLoader.loadHeuristicFromFile(hpath);
		}
		
		SearchProblemAlgorithm<StringState> alg = ALGO_MAP.get(algStr);
		if (alg != null) {
			var result = alg.executeAlgorithm(problem, h);
			String algName = alg.getAlgorithmName();
			String addition = algName.equals("A-STAR") ? hpathStr : "";
			System.out.println("# " + algName + " " + addition);
			System.out.println(result);
			return;
		}
		
		if (optimisticCheck) {
			System.out.println("# HEURISTIC-OPTIMISTIC " + hpathStr);
			HeuristicFunctionChecker.checkAndReportOptimistic(problem, h);
			return;
		}
		
		if (consistencyCheck) {
			System.out.println("# HEURISTIC-CONSISTENT " + hpathStr);
			HeuristicFunctionChecker.checkAndReportConsistent(problem, h);
			return;
		}
		
		
	}

}
