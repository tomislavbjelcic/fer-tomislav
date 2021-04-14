package task6;

public class IntPair {
	public int first;
	public int second;
	
	public IntPair(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", first, second);
	}
}
