package ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import ui.algorithms.BreadthFirstSearch;
import ui.loaders.StringStateSearchProblemLoader;

public class Main {
	
	public static void main(String[] args) {
		
		String fstr = "./maps/istra.txt";
		Path p = Paths.get(fstr);
		var prob = StringStateSearchProblemLoader.loadProblemFromFile(p);
		System.out.println("Done Loading problem.");
		System.out.println("Applying algo...");
		SearchProblemAlgorithm<StringState> alg = new BreadthFirstSearch<>();
		SearchProblemResult res = alg.executeAlgorithm(prob, null);
		System.out.println("Algo done: " + alg.getAlgorithmName());
		System.out.println(res);
		
		
		
		/*
		String str = "sm";
		String regex = "\\s+";
		String[] spl = str.split(regex);
		System.out.println("" + spl.length + '\n' + Arrays.toString(spl));
		*/
	}
	
}
