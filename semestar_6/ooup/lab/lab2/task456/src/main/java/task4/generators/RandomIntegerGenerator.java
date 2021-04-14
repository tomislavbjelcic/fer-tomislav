package task4.generators;

import java.util.Random;

import task4.IntegerGenerator;

public class RandomIntegerGenerator implements IntegerGenerator {

	private double mean;
	private double stdeviation;
	private int elementCount;
	private Random rng = new Random();

	public RandomIntegerGenerator(double mean, double stdeviation, int elementCount) {
		checkParameters(mean, stdeviation, elementCount);
		this.mean = mean;
		this.stdeviation = stdeviation;
		this.elementCount = elementCount;
	}

	private static void checkParameters(double mean, double stdeviation, int elementCount) {
		if (elementCount < 0)
			throw new IllegalArgumentException("Element count is negative: " + elementCount);
		if (stdeviation < 0.0)
			throw new IllegalArgumentException("Standard deviation is negative: " + stdeviation);
	}

	public int generateSingle() {
		double unitNormalGen = rng.nextGaussian(); // N(0, 1)

		/*Ako je X ~ N(0, 1), 
		 * tada (aX + b) ~ N(b, a^2)*/
		double normalGen = unitNormalGen * stdeviation + mean;
		int toInt = (int) Math.round(normalGen);
		return toInt;
	}

	@Override
	public int[] generate() {
		int[] array = new int[elementCount];
		for (int i=0; i<elementCount; i++)
			array[i] = generateSingle();
		return array;
	}

}
