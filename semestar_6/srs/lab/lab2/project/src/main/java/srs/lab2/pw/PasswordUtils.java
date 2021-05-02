package srs.lab2.pw;

import java.io.Console;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PasswordUtils {
	
	private static final SecureRandom SECURE_RNG = new SecureRandom();
	private static final int SALT_LENGTH_BYTES = 32;
	private static final int ITERATIONS = 100_000;
	
	private PasswordUtils() {}
	
	
	public static byte[] generateRandomBytes(int len, Random rng) {
		byte[] bytes = new byte[len];
		rng.nextBytes(bytes);
		return bytes;
	}
	
	public static byte[] generateSalt() {
		return generateRandomBytes(SALT_LENGTH_BYTES, SECURE_RNG);
	}
	
	public static byte[] generatePasswordHash(char[] password, PasswordHasher hasher, byte[] salt) {
		byte[] hash =  hasher.hashPassword(password, salt, ITERATIONS);
		return hash;
	}
	
	public static boolean checkPasswordMatch(char[] enteredPassword, byte[] existingHash, PasswordHasher hasher, byte[] salt) {
		byte[] hashEntered = hasher.hashPassword(enteredPassword, salt, ITERATIONS);
		
		boolean same = Arrays.equals(hashEntered, existingHash);
		return same;
	}
	
	public char[] getPasswordFromConsole(String prompt) {
		Console console = Objects.requireNonNull(System.console(), "System.console() returned null");
		char[] pw = console.readPassword(prompt);
		return pw;
	}
	
}
