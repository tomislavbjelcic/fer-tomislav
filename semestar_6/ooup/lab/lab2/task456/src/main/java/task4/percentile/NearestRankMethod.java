package task4.percentile;

import task4.PercentileMethod;

public class NearestRankMethod implements PercentileMethod {

	@Override
	public double getPercentile(int[] sortedSequence, double percentile) {
		Util.defaultCheckPercentileArguments(sortedSequence, percentile);
		if (percentile == 0.0)
			return sortedSequence[0];
		int N = sortedSequence.length;
		double posDbl = (N*percentile) / 100.0;
		int pos = ((int) Math.ceil(posDbl)) - 1;
		return sortedSequence[pos];
	}

}
