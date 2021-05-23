package ui;

import java.math.RoundingMode;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import ui.learn.ID3DecisionTree;
import ui.learn.Node;
import ui.loaders.DataLoader;
import ui.loaders.LoadedData;

public class Solution {
	
	/*
	private static class Pair {
		String pred;
		String real;
		
		Pair(String pred, String real) {
			this.pred = pred;
			this.real = real;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (this == obj)
				return true;
			if (this.getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			return this.pred.equals(other.pred) && this.real.equals(other.real);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(pred, real);
		}
	}*/
	
	private static DecimalFormat df(int decCount) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		otherSymbols.setDecimalSeparator('.');
		String fmt = "0." + "0".repeat(decCount);
		DecimalFormat df = new DecimalFormat(fmt, otherSymbols);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df;
	}
	
	public static void main(String[] args) {
		
		int argsLen = args.length;
		if (argsLen < 2 || argsLen > 3) {
			System.out.println("Broj argumenata nije 2 ili 3: " + argsLen);
			return;
		}
		
		String trainingPathStr = args[0];
		Path trainingPath = Path.of(trainingPathStr);
		
		String testPathStr = args[1];
		Path testPath = Path.of(testPathStr);
		
		DataLoader loader = new DataLoader(FeaturesImpl::new);
		LoadedData trainData = loader.loadCsv(trainingPath);
		
		ID3DecisionTree id3 = argsLen==3 ? new ID3DecisionTree(Integer.parseInt(args[2])) : new ID3DecisionTree();
		
		id3.fit(trainData.dataUnits, trainData.features);
		
		var process = id3.getIgCalcProcess();
		DecimalFormat df = df(4);
		
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
		
		System.out.println("[BRANCHES]:");
		Node root = id3.getRoot();
		root.printBranches();
		
		
		LoadedData testData = loader.loadCsv(testPath);
		
		Set<String> outs = trainData.features.getOutputValues();
		List<String> outsList = new ArrayList<>(outs);
		Collections.sort(outsList);
		
		Map<String, Integer> indexMap = new HashMap<>();
		int outsListLen = outsList.size();
		for (int i=0; i<outsListLen; i++) {
			String o = outsList.get(i);
			indexMap.put(o, i);
		}
		
		int[][] confMatrix = new int[outsListLen][outsListLen];
		
		List<String> preds = testData.dataUnits.stream().map(id3::predict).collect(Collectors.toCollection(ArrayList::new));
		String outClass = trainData.features.getOutputType();
		List<String> correct = testData.dataUnits.stream().map(m -> m.get(outClass)).collect(Collectors.toCollection(ArrayList::new));
		
		int total = preds.size();
		int accurate = 0;
		for (int i=0; i<total; i++) {
			String predicted = preds.get(i);
			String real = correct.get(i);
			int i1 = indexMap.get(real);
			int i2 = indexMap.get(predicted);
			confMatrix[i1][i2]++;
			if (i1==i2)
				accurate++;
		}
		
		double accuracy = (1.0 * accurate) / total;
		DecimalFormat df2 = df(5);
		String accStr = df2.format(accuracy);
		
		System.out.print("[PREDICTIONS]: ");
		System.out.println(preds.stream().collect(Collectors.joining(" ")));
		System.out.println("[ACCURACY]: " + accStr);
		System.out.println("[CONFUSION_MATRIX]: ");
		for (int i=0; i<outsListLen; i++) {
			for (int j=0; j<outsListLen; j++) {
				System.out.printf("" + confMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		
	}

}
