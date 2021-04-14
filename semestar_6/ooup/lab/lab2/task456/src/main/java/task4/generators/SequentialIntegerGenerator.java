package task4.generators;

import java.util.ArrayList;
import java.util.List;

import task4.IntegerGenerator;

public class SequentialIntegerGenerator implements IntegerGenerator {
	
	private int bottomLimit;
	private int topLimit;
	private int step;
	
	public SequentialIntegerGenerator(int bottomLimit, int topLimit, int step) {
		checkParameters(bottomLimit, topLimit, step);
		this.bottomLimit = bottomLimit;
		this.topLimit = topLimit;
		this.step = step;
	}
	
	private static void checkParameters(int bottomLimit, int topLimit, int step) {
		if (topLimit < bottomLimit)
			throw new IllegalArgumentException("Top limit " + topLimit + " is less than bottom limit " + bottomLimit);
		if (step < 1)
			throw new IllegalArgumentException("Step less than 1: " + step);
	}
	
	@Override
	public int[] generate() {
		List<Integer> list = new ArrayList<>();
		for (int current=bottomLimit; current<topLimit; current+=step) {
			list.add(current);
		}
		int size = list.size();
		int[] array = new int[size];
		for (int i=0; i<size; i++)
			array[i] = list.get(i).intValue();
		return array;
	}

}
