package ui.learn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import ui.ArgMaxResult;
import ui.Features;
import ui.Util;

public class ID3DecisionTree {
	
	private static final Function<Entry<String, Integer>, Integer> EE1 = Entry::getValue;
	//private static final Function<Entry<String, Integer>, String> KE1 = Entry::getKey;
	
	
	private int depth;
	private boolean depthLimited;
	
	private Node root = null;
	
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
		
		// pobroji svaku oznaku
		Map<String, Integer> countMap = Util.count(selectedSet, m -> m.get(outClass), null);
		var entrySet = countMap.entrySet();
		// pronadji najcesce oznake
		
		ArgMaxResult<Map.Entry<String, Integer>, Integer> maxRes = Util.argMax(entrySet, EE1, Comparator.naturalOrder(), LinkedList::new);
		var freqEntry = maxRes.args.get(0);
		// IMAPOSLA
		if (feats.isEmpty() || (freqEntry.getValue() == selectedSet.size()))
			return Node.leaf(freqEntry.getKey());
		//IMAPOSLA
		return null;
	}
	
	public void fit() {
		
	}
	
	public void predict() {
		
	}
	
	
}
