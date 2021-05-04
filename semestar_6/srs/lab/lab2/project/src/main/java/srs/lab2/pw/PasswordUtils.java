package srs.lab2.pw;

import java.io.Console;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Razred koji sadrži korisne metode za rukovanje lozinkama.
 * 
 * @author tomislav
 *
 */
public class PasswordUtils {
	
	/**
	 * Kriptografski generator slučajnih brojeva.
	 */
	private static final SecureRandom SECURE_RNG = new SecureRandom();
	/**
	 * Duljina slučajnog salta u bajtovima.
	 */
	private static final int SALT_LENGTH_BYTES = 32;
	/**
	 * Korišten broj iteracija za računanje hasheva lozinki.
	 */
	private static final int ITERATIONS = 1 << 16;
	
	private PasswordUtils() {}
	
	
	/**
	 * Generira polje duljine {@code len} sa slučajnim vrijednostima koristeći generator slučajnih 
	 * brojeva {@code rng}.
	 * 
	 * @param len
	 * @param rng
	 * @return
	 */
	public static byte[] generateRandomBytes(int len, Random rng) {
		byte[] bytes = new byte[len];
		rng.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * Generira slučajan salt od 32 bajta koristeći kriptografski generator slučajnih brojeva.
	 * 
	 * @return
	 */
	public static byte[] generateSalt() {
		return generateRandomBytes(SALT_LENGTH_BYTES, SECURE_RNG);
	}
	
	/**
	 * Računa hash lozinke {@code password} pomoću objekta {@code hasher} i slučajnog {@code salt}.
	 * 
	 * @param password
	 * @param hasher
	 * @param salt
	 * @return
	 */
	public static byte[] generatePasswordHash(char[] password, PasswordHasher hasher, byte[] salt) {
		byte[] hash =  hasher.hashPassword(password, salt, ITERATIONS);
		return hash;
	}
	
	/**
	 * Provjerava odgovara li lozinka {@code enteredPassword} hashiranoj lozinki {@code existingHash} pri čemu je korišten 
	 * slučajan {@code salt}.
	 * 
	 * @param enteredPassword
	 * @param existingHash
	 * @param hasher
	 * @param salt
	 * @return
	 */
	public static boolean checkPasswordMatch(char[] enteredPassword, byte[] existingHash, PasswordHasher hasher, byte[] salt) {
		byte[] hashEntered = hasher.hashPassword(enteredPassword, salt, ITERATIONS);
		
		boolean same = Arrays.equals(hashEntered, existingHash);
		return same;
	}
	
	/**
	 * Na interaktivnu konzolu ispisuje poruku {@code prompt} i dohvaća korisnikov unos koji se ne ispisuje 
	 * tijekom korisnikovog unosa. Koristi se za unos lozinki.
	 * 
	 * @param prompt
	 * @return
	 */
	public static char[] getPasswordFromConsole(String prompt) {
		Console console = Objects.requireNonNull(System.console(), "System.console() returned null");
		char[] pw = console.readPassword(prompt);
		return pw;
	}
	
	
}
