package srs.lab2;

import java.util.Arrays;

import srs.lab2.pw.PBKDF2;
import srs.lab2.pw.PasswordHasher;
import srs.lab2.pw.PasswordUtils;

public class Main {
	
	public static void main(String[] args) {
		
		PasswordHasher hasher = new PBKDF2();
		String pwStr = "$1fr4";
		char[] pw = pwStr.toCharArray();
		
		byte[] salt = new byte[] {1, -20, -111, 81, 68, -9, -107, -76, 62, -29, 
				-106, 9, -69, 91, 30, -7, -99, 56, -105, -97, 123, 31, 20, -79, -99, 45, 18, -121, 73, -63, 8, -50};
		//System.out.println(Arrays.toString(salt));
		byte[] hash = PasswordUtils.generatePasswordHash(pw, hasher, salt);
		
		System.out.println(hash.length);
		System.out.println(Arrays.toString(hash));
		
	}
	
}
