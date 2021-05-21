package ui;

public class Solution {

	public static void main(String ... args) {
		System.out.println("Ovime krece Vas program.");
		for(String arg : args) {
			System.out.printf("Predan argument %s%n", arg);
		}
	}

}
