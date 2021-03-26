package srs.lab1.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred koji sadrži pomoćne metode za kriptografske ključeve.
 * 
 * @author tomislav
 *
 */
public class KeyUtils {
	
	/**
	 * Broj bitova AES kriptografskog ključa.
	 */
	public static final int AES_KEY_LENGTH = 128;
	/**
	 * Key derivation function algorithm.
	 */
	public static final String KEY_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA" + AES_KEY_LENGTH;
	/**
	 * Broj iteracija primjenjivanja funkcije.
	 */
	public static final int ITERATIONS = 4096;
	
	private KeyUtils() {}
	
	/**
	 * Stvara objekt AES ključa koristeći šifru {@code password} i salt {@code salt}.
	 * 
	 * @param password
	 * @param salt
	 * @return objekt AES ključa.
	 */
	public static SecretKey generateAESKeyFromPassword(char[] password, byte[] salt) {
		
		// stvori objekt generator ključeva
		SecretKeyFactory factory = null;
		try {
			factory = SecretKeyFactory.getInstance(KEY_DERIVATION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// stvori specifikaciju ključa koristeći potrebne podatke: password i salt
        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, AES_KEY_LENGTH);
        
        // generiraj bajtove ključa iz specifikacije
        byte[] keyBytes = null;
        try {
			keyBytes = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // iz bajtova ključa stvori AES ključ
        SecretKey aesKey = new SecretKeySpec(keyBytes, "AES");
		
		return aesKey;
	}
	
}
