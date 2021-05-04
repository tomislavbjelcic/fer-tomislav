package srs.lab2.pw;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Predstavlja implementaciju {@link PasswordHasher} sučelja koja koristi algoritam {@code PBKDF2WithHmacSHA256} kako bi 
 * računala sažetke lozinki.<br>
 * 
 * @author tomislav
 *
 */
public class PBKDF2 implements PasswordHasher {
	
	/**
	 * Duljina sažetka u bitovima.
	 */
	public static final int HASH_LENGTH_BITS = 256;
	/**
	 * Korišteni algoritam.
	 */
	public static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	
	/**
	 * Objekt tvornice ključeva inicijalizirana da koristi prikladan algoritam.
	 */
	private static final SecretKeyFactory FACTORY = initFactory();
	
	/**
	 * Dohvaća objekt tvornice ključeva.
	 */
	private static SecretKeyFactory initFactory() {
		SecretKeyFactory factory = null;
		try {
			factory = SecretKeyFactory.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return factory;
	}
	
	@Override
	public byte[] hashPassword(char[] password, byte[] salt, int iterations) {
		KeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_LENGTH_BITS);
		
		SecretKey sk = null;
		try {
			sk = FACTORY.generateSecret(spec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] hash = sk.getEncoded();
		return hash;
	}

}
