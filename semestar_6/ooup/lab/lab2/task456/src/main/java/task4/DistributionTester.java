package task4;

import java.util.Arrays;
import java.util.Objects;

public class DistributionTester {
	
	private static final double percFirst = 10.0;
	private static final double percLast = 90.0;
	private static final double percStep = 10.0;
	
	private IntegerGenerator gen;
	private PercentileMethod pm;
	
	public IntegerGenerator getIntegerGenerator() {
		return gen;
	}
	
	public void setIntegerGenerator(IntegerGenerator gen) {
		this.gen = Objects.requireNonNull(gen);
	}
	
	public PercentileMethod getPercentileMethod() {
		return pm;
	}
	
	public void setPercentileMethod(PercentileMethod pm) {
		this.pm = Objects.requireNonNull(pm);
	}
	
	public void test() {
		if (gen == null) {
			System.out.println("Integer generator not set!");
			return;
		}
		if (pm == null) {
			System.out.println("Percentile method not set!");
			return;
		}
		
		int[] generated = gen.generate();
		Arrays.sort(generated);
		System.out.println("Generated sequence: " + Arrays.toString(generated));
		for (double p=percFirst; p<=percLast; p+=percStep) {
			System.out.println("" + p + "-th percentile: " + pm.getPercentile(generated, p));
		}
	}
	
	
	
}
