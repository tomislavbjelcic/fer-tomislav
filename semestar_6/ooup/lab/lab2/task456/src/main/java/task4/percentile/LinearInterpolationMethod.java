package task4.percentile;

import task4.PercentileMethod;

public class LinearInterpolationMethod implements PercentileMethod {
	
	private static double percentileRank(int i, int N) {
		return 100.0*((i+1)-0.5) / N;
	}
	
	@Override
	public double getPercentile(int[] sortedSequence, double percentile) {
		Util.defaultCheckPercentileArguments(sortedSequence, percentile);
		
		int N = sortedSequence.length;
		if (N == 1)
			return sortedSequence[0];
		double pv0 = percentileRank(0, N);
		if (percentile <= pv0)
			return sortedSequence[0];
		double pvN = percentileRank(N-1, N);
		if (percentile >= pvN)
			return sortedSequence[N-1];
		
		double retval = 0.0;
		for (int i=1; i<N; i++) {
			double pvip1 = percentileRank(i, N);
			if (pvip1 == percentile)
				return sortedSequence[i];
			if (percentile < pvip1) {
				double prev = percentileRank(i-1, N);
				retval = sortedSequence[i-1] 
						+ N*(percentile-prev)*(sortedSequence[i]-sortedSequence[i-1])/100.0;
				break;
			}
		}
		return retval;
	}
	
	
	
}
