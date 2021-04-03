package ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ui.algorithms.AStarSearch;
import ui.algorithms.BreadthFirstSearch;
import ui.algorithms.UniformCostSearch;
import ui.loaders.StringStateHeuristicFunctionLoader;
import ui.loaders.StringStateSearchProblemLoader;

public class Main {
	
	public static void main(String[] args) {
		
		List<SearchProblemAlgorithm<StringState>> algs = new ArrayList<>();
		algs.add(new BreadthFirstSearch<>());
		algs.add(new UniformCostSearch<>());
		algs.add(new AStarSearch<>());
		
		String fstr = "./maps/3x3_puzzle.txt";
		Path pf = Paths.get(fstr);
		
		String hpstr = "./maps/3x3_misplaced_heuristic.txt";
		Path ph = Paths.get(hpstr);
		
		
		var h = StringStateHeuristicFunctionLoader.loadHeuristicFromFile(ph);
		var prob = StringStateSearchProblemLoader.loadProblemFromFile(pf);
		System.out.println("Done Loading problem & heuristic.");
		System.out.println("Applying algo...");
		SearchProblemAlgorithm<StringState> alg = algs.get(2);
		SearchProblemResult res = alg.executeAlgorithm(prob, h);
		System.out.println("Algo done: " + alg.getAlgorithmName());
		System.out.println(res);
		
		
		
		
	}
	
}
