package task4.percentile;

import java.util.Objects;

public class Util {
	
	private Util() {}
	
	public static void defaultCheckPercentileArguments
			(int[] sortedSequence, double percentile) {
		Objects.requireNonNull(sortedSequence, "Given sequence is null.");
		if (percentile < 0.0 || percentile > 100.0)
			throw new IllegalArgumentException("Percentile order out of [0, 100] range: " + percentile);
		if (sortedSequence.length == 0)
			throw new IllegalArgumentException("Cannot determine percentile of sequence with no elements.");
		boolean isSorted = checkSorted(sortedSequence);
		if (!isSorted)
			throw new IllegalArgumentException("Given sequence is not sorted.");
	}
	
	private static boolean checkSorted(int[] seq) {
		int len = seq.length;
		if (len < 2)
			return true;
		for (int i=1; i<len; i++) {
			if (seq[i] < seq[i-1])
				return false;
		}
		return true;
	}
	
}
