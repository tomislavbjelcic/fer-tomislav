package srs.lab2;

import java.io.Console;

public class Main {
	
	public static void main(String[] args) {
		
		Console cons = System.console();
		String prompt = "Password: ";
		char[] pwdChars = cons.readPassword(prompt);
		String pwdStr = String.valueOf(pwdChars);
		System.out.println("Entered: " + pwdStr);
		System.out.println("Len: " + pwdStr.length());
		
	}
	
}
