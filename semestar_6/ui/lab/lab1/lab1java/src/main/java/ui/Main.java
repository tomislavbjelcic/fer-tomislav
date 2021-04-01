package ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import ui.loaders.StringStateSearchProblemLoader;

public class Main {
	
	public static void main(String[] args) {
		Path p = Paths.get("./maps/istra.txt");
		var prob = StringStateSearchProblemLoader.loadProblemFromFile(p);
		System.out.println("Done");
	}
	
}
