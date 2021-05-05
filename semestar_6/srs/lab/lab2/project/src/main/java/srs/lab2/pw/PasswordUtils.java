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
	private static final int ITERATIONS = 50;
	/**
	 * Regularni izraz koji odgovara ispravnom korisničkom imenu.
	 */
	private static final String USERNAME_REGEX = "\\w+";
	/**
	 * Minimalna duljina znakova lozinke.
	 */
	private static final int PASSWORD_MIN_LENGTH = 8;
	
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
	
	/**
	 * Provjerava ispravnost usernamea (korisničkog imena) i vraća poruku pogreške ako postoji, inače {@code null}.<br>
	 * Username je ispravan ako se sastoji samo od slova, dekadskih znamenaka i podvlaka.
	 * 
	 * @param username
	 * @return
	 */
	public static String checkUsername(String username) {
		boolean match = username.matches(USERNAME_REGEX);
		if (!match)
			return "Username can only consist of letters, numbers and underscores!";
		return null;
	}
	
	/**
	 * Provjerava kompleksnost lozinke i vraća poruku pogreške ako postoji, inače {@code null}.<br>
	 * Lozinka će proći provjeru ako se sastoji od barem jednog malog slova, barem jednog velikog slova 
	 * i barem jedne dekadske znamenke.
	 * 
	 * @param password
	 * @return
	 */
	public static String checkPassword(char[] password) {
		int len = password.length;
		if (len < PASSWORD_MIN_LENGTH)
			return "Password has to have length " + PASSWORD_MIN_LENGTH + " or more.";
		
		boolean uppercases = false;
		boolean lowercases = false;
		boolean digits = false;
		for (char c : password) {
			uppercases = uppercases || Character.isUpperCase(c);
			lowercases = lowercases || Character.isLowerCase(c);
			digits = digits || Character.isDigit(c);
			if (uppercases && lowercases && digits)
				break;
		}
		
		if (!(uppercases && lowercases && digits))
			return "Password must consist of at least 1 upper case letter, "
						+ "1 lower case letter and 1 number.";
		
		return null;
	}
	
	
}
