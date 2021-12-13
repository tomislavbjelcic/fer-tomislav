package kik.lab2;

import java.math.BigInteger;

public class Main {
	
	public static void main(String[] args) {
		
		String numstr = "000k";
		BigInteger bi = new BigInteger(numstr, 16);
		System.out.println(bi);
		
	}
	
}
