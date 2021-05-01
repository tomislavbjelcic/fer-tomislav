package ui;

public class UnorderedPair<T> {
	
	private T first;
	private T second;
	
	public UnorderedPair() {
		this(null, null);
	}
	
	public UnorderedPair(T first, T second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}
	
	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
	
	
}
