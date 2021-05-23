package ui.learn;

import java.util.Map;

class Node {
	
	String value;
	Map<String, Node> children;
	
	Node(String value, Map<String, Node> children) {
		this.value = value;
		this.children = children;
	}
	
	
	static Node leaf(String value) {
		return new Node(value, null);
	}
	
	boolean isLeaf() {
		boolean isLeaf = (children == null) || children.isEmpty();
		return isLeaf;
	}
	
}
