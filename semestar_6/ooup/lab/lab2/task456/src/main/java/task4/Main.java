package task4;

import task4.generators.*;
import task4.percentile.*;

public class Main {
	
	public static void main(String[] args) {
		
		IntegerGenerator gen = null;
		PercentileMethod pm = null;
		
		// promijeniti u koje god konkretne strategije
		gen = new FibonacciIntegerGenerator(15);
		pm = new LinearInterpolationMethod();
		
		DistributionTester tester = new DistributionTester();
		tester.setIntegerGenerator(gen);
		tester.setPercentileMethod(pm);
		
		tester.test();
		
	}
	
}
