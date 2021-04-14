package task5.listeners;

import java.util.Arrays;
import java.util.Collection;

public class MedianPrintListener extends ReducePrintListener<Double> {

	public MedianPrintListener() {
		super("Median");
	}

	@Override
	public Double reduce(Collection<Integer> col) {
		int size = col.size();
		if (size == 0)
			return null;
		
		Integer[] ints = new Integer[size];
		col.toArray(ints);
		Arrays.sort(ints);
		
		int midindex = (size-1)/2;
		boolean isEven = size%2 == 0;
		double median = 0.0;
		if (isEven) {
			int e1 = ints[midindex];
			int e2 = ints[midindex+1];
			median = (e1+e2) / 2.0;
		} else
			median = ints[midindex].doubleValue();
		return median;
	}

}
