package ui;

import java.nio.file.Path;

import ui.loaders.DataLoader;
import ui.loaders.LoadedData;

public class Main {
	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("args.length not exactly 1");
			return;
		}
		
		String pathStr = args[0];
		Path path = Path.of(pathStr);
		
		DataLoader loader = new DataLoader(FeaturesImpl::new);
		LoadedData data = loader.loadCsv(path);
		
		var fl = data.features.getFeatureList();
		System.out.println("Feature list: " + fl);
		for (String feat : fl) {
			System.out.println(feat);
		}
		var out = data.features.getOutputType();
		var outVals = data.features.getOutputValues();
		System.out.println("Out: " + out);
		System.out.println(outVals);
		
		data.dataUnits.forEach(System.out::println);
		
	}
	
}
