package ui;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ui.learn.ID3DecisionTree;
import ui.learn.Node;
import ui.loaders.DataLoader;
import ui.loaders.LoadedData;

public class Main {
	
	private static DecimalFormat df() {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.0000", otherSymbols);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df;
	}
	
	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.out.println("args.length not exactly 2");
			return;
		}
		
		String pathStr = args[0];
		Path path = Path.of(pathStr);
		
		String testPathStr = args[1];
		Path testPath = Path.of(testPathStr);
		
		DataLoader loader = new DataLoader(FeaturesImpl::new);
		LoadedData data = loader.loadCsv(path);
		
		var fl = data.features.getFeatureList();
		System.out.println("Feature list: " + fl);
		
		var out = data.features.getOutputType();
		var outVals = data.features.getOutputValues();
		System.out.println("Out: " + out);
		System.out.println("Out vals: " + outVals);
		
		data.dataUnits.forEach(System.out::println);
		
		ID3DecisionTree id3 = new ID3DecisionTree();
		id3.fit(data.dataUnits, data.features);
		System.out.println("Done");
		var process = id3.getIgCalcProcess();
		DecimalFormat df = df();
		
		Comparator<Entry<String, Double>> vcomp = Comparator.comparing(Entry::getValue);
		Comparator<Entry<String, Double>> vcomprev = vcomp.reversed();
		Comparator<Entry<String, Double>> ecomp = vcomprev.thenComparing(Entry::getKey);
		for (var e : process.entrySet()) {
			var v = e.getValue();
			String joined = v.entrySet().stream().sorted(ecomp).map(en -> {
				String ds = df.format(en.getValue().doubleValue());
				String s = "IG(" + en.getKey() + ")=" + ds;
				return s;
			}).collect(Collectors.joining(" "));
			System.out.println(joined);
		}
		
		Node r = id3.getRoot();
		r.printBranches();
		
		System.out.println("Predictions...");
		LoadedData testData = loader.loadCsv(testPath);
		
		testData.dataUnits.forEach(m -> System.out.printf(id3.predict(m) + " "));
		System.out.println();
		
		
	}
	
}
