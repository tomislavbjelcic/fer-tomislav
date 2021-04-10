package task4.generators;

import task4.IntegerGenerator;

public class FibonacciIntegerGenerator implements IntegerGenerator {
	
	public static final int FIRST = 0;
	public static final int SECOND = 1;
	
	private int elementCount;
	
	public FibonacciIntegerGenerator(int elementCount) {
		if (elementCount < 0)
			throw new IllegalArgumentException("Element count is negative: " + elementCount);
		this.elementCount = elementCount;
	}
	
	@Override
	public int[] generate() {
		if (elementCount < 3) {
			int[] arr = switch(elementCount) {
			case 0 -> new int[] {};
			case 1 -> new int[] {FIRST};
			case 2 -> new int[] {FIRST, SECOND};
			default -> null;
			};
			return arr;
		}
		
		int[] arr = new int[elementCount];
		arr[0] = FIRST;
		arr[1] = SECOND;
		
		for (int i=2; i<elementCount; i++)
			arr[i] = arr[i-1] + arr[i-2];
		return arr;
	}
	
	
	
}
