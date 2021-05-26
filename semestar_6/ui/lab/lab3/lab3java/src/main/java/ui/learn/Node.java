package ui.learn;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

class Pair {
	int level;
	Node n;
	
	public Pair(int level, Node n) {
		this.level = level;
		this.n = n;
	}
}

public class Node {
	
	
	
	public String value;
	public String defaultVal;
	public Map<String, Node> children;
	
	public Node(String value, Map<String, Node> children, String defaultVal) {
		this.value = value;
		this.children = children;
		this.defaultVal = defaultVal;
	}
	
	
	public static Node leaf(String value) {
		return new Node(value, null, value);
	}
	
	public boolean isLeaf() {
		boolean isLeaf = (children == null) || children.isEmpty();
		return isLeaf;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public void printBranches() {
		LinkedList<String> seq = new LinkedList<>();
		pb(this, 1, seq);
		
	}
	
	private void pb(Node n, int level, LinkedList<String> seq) {
		if (n.isLeaf()) {
			String s = seq.stream().collect(Collectors.joining(" "));
			System.out.println(s + " " + n.value);
			return;
		}
		
		
		for (var e : n.children.entrySet()) {
			Node child = e.getValue();
			String fv = e.getKey();
			String ss = "" + level + ":" + n.value + "=" + fv;
			seq.add(ss);
			pb(child, level+1, seq);
			seq.removeLast();
		}
	}
	
}
