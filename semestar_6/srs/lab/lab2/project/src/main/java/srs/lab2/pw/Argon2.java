package srs.lab2.pw;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

/**
 * Predstavlja implementaciju {@link PasswordHasher} sučelja koja koristi algoritam {@code Argon2}.
 * 
 * @author tomislav
 *
 */
public class Argon2 implements PasswordHasher {
	
	private static final int HASH_LENGTH_BYTES = 32;
	
	@Override
	public byte[] hashPassword(char[] password, byte[] salt, int iterations) {
		
		Argon2Parameters params = new Argon2Parameters
				.Builder(Argon2Parameters.ARGON2_id)
				.withSalt(salt)
				.withIterations(iterations)
				.build();
				// može se podesiti i još parametara
		Argon2BytesGenerator generator = new Argon2BytesGenerator();
		generator.init(params);
		
		byte[] hash = new byte[HASH_LENGTH_BYTES];
		generator.generateBytes(password, hash);
		return hash;
	}

}
