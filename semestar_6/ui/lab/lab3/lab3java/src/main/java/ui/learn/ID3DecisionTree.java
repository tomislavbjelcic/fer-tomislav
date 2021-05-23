package ui.learn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import ui.ArgMaxResult;
import ui.Features;
import ui.Util;

public class ID3DecisionTree {
	
	private static final Function<Entry<String, Integer>, Integer> EE1 = Entry::getValue;
	private static final Comparator<Double> DBL_CMP = Double::compare;
	private static final Comparator<Map.Entry<String, Integer>> VAL_FIRST_CMP 
				= (e1, e2) -> {
					int cmpval = e1.getValue().compareTo(e2.getValue());
					if (cmpval != 0)
						return cmpval;
					return e1.getKey().compareTo(e2.getKey());
				};
	//private static final Function<Entry<String, Integer>, String> KE1 = Entry::getKey;
	
	
	private int depth;
	private boolean depthLimited;
	
	private Node root = null;
	private Map<Integer, Map<String, Double>> igCalcProcess = new LinkedHashMap<>();
	private int igCalcProcessIndex = 1;
	
	private BiConsumer<String, Double> adder = (f,ig) -> {
		Map<String, Double> m = igCalcProcess.get(igCalcProcessIndex);
		if (m == null) {
			m = new HashMap<>();
			igCalcProcess.put(igCalcProcessIndex, m);
		}
		
		m.put(f, ig);
	};
	
	public ID3DecisionTree() {
		this(1, false);
	}
	
	public ID3DecisionTree(int depth) {
		this(depth, true);
	}
	
	private ID3DecisionTree(int depth, boolean depthLimited) {
		if (depthLimited && depth < 0)
			throw new IllegalArgumentException("Negative depth: " + depth);
		
		this.depthLimited = depthLimited;
		this.depth = depth;
	}
	
	private Node id3(Set<Map<String, String>> selectedSet, Set<Map<String, String>> parentSet, 
			Set<String> feats, String outClass, Features features, int currentDepth) {
		
		Util.MapValueExtractor<String, String> extrac = new Util.MapValueExtractor<>();
		extrac.key = outClass;
		
		if (selectedSet.size() == 0) {
			Map<String, Integer> countMap = Util.count(parentSet, extrac, HashMap::new);
			var entrySet = countMap.entrySet();
			var maxRes = Util.argMax(entrySet, EE1, Comparator.naturalOrder(), ArrayList::new);
			Collections.sort(maxRes.args, VAL_FIRST_CMP);
			return Node.leaf(maxRes.args.get(0).getKey());
		}
		
		// pobroji svaku oznaku
		Map<String, Integer> countMap = Util.count(selectedSet, extrac, HashMap::new);
		var entrySet = countMap.entrySet();
		// pronadji najcesce oznake
		
		ArgMaxResult<Map.Entry<String, Integer>, Integer> maxRes = Util.argMax(entrySet, EE1, Comparator.naturalOrder(), ArrayList::new);
		Collections.sort(maxRes.args, VAL_FIRST_CMP);
		var freqEntry = maxRes.args.get(0);
		if (feats.isEmpty() || (freqEntry.getValue() == selectedSet.size()) || (depthLimited && currentDepth==0))
			return Node.leaf(freqEntry.getKey());
		
		Function<String, Set<? extends String>> gvf = features::getValues;
		ArgMaxResult<String, Double> x = Util.argMax(feats, 
				f -> Util.informationGain(selectedSet, f, outClass, gvf), 
				DBL_CMP, ArrayList::new, adder);
		igCalcProcessIndex++;
		
		//Collections.sort(x.args); //????
		String maxIgFeat = x.args.get(0);
		var vals = gvf.apply(maxIgFeat);
		Node n = new Node(maxIgFeat, new HashMap<>(), freqEntry.getKey());
		Util.MapKeyValueEqualsPredicate<String, String> eqp = new Util.MapKeyValueEqualsPredicate<>();
		
		Set<String> featRemoved = new HashSet<>(feats);
		featRemoved.remove(maxIgFeat);
		eqp.key = maxIgFeat;
		for (String v : vals) {
			eqp.obj = v;
			Set<Map<String, String>> subset = Util.subset(selectedSet, HashSet::new, eqp);
			Node branch = id3(subset, selectedSet, featRemoved, outClass, features, currentDepth-1);
			n.children.put(v, branch);
		}
		
		return n;
	}
	
	public Map<Integer, Map<String, Double>> getIgCalcProcess() {
		return igCalcProcess;
	}
	
	public void fit(Set<Map<String, String>> data, Features features) {
		if (root != null)
			return;
		Set<String> feats = new HashSet<>(features.getFeatureList());
		String outClass = features.getOutputType();
		root = this.id3(data, data, feats, outClass, features, depth);
	}
	
	public String predict(Map<String, String> data) {
		if (root == null)
			return null;
		
		Node n = root;
		boolean b = true;
		while(b) {
			String f_or_val = n.value;
			if (n.isLeaf())
				return f_or_val;
			
			String featVal = data.get(f_or_val);
			Objects.requireNonNull(featVal);
			
			Node child = n.children.get(featVal);
			if (child == null)
				return n.defaultVal;
			
			n=child;
		}
		
		return null;
	}
	
	public Node getRoot() {
		return root;
	}
	
	
}
