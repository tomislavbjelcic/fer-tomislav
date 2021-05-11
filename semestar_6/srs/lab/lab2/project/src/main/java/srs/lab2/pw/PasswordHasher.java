package srs.lab2.pw;

/**
 * Objekt koji predstavlja kriptografsku funkciju sažetka za lozinke.
 * 
 * @author tomislav
 *
 */
public interface PasswordHasher {
	
	/**
	 * Računa sažetak (niz bajtova) neke lozinke {@code password} zajedno sa slučajnom vrijednosti {@code salt}.<br>
	 * Kako bi računanje sažetka bilo sporije, postupak se ponavlja {@code iterations} puta.
	 * 
	 * @param password
	 * @param salt
	 * @param iterations
	 * @return niz bajtova koji predstavlja sažetak.
	 */
	byte[] hashPassword(char[] password, byte[] salt, int iterations);
	
}
