package srs.lab2;

import java.nio.file.Path;

import srs.lab2.pw.PBKDF2;
import srs.lab2.pw.PasswordHasher;
import srs.lab2.vault.VaultLoader;

/**
 * Razred sa globalnim podacima za sve naredbe.
 * 
 * @author tomislav
 *
 */
class Common {
	
	/**
	 * Putanja do spremnika podataka.
	 */
	static final Path VAULT_PATH = Path.of("./data/vault.txt");
	/**
	 * Objekt koji učitava spremnik iz datoteke.
	 */
	static final VaultLoader LOADER = new VaultLoader();
	/**
	 * Korištena implementacija funkcije sažetka za lozinke.
	 */
	static final PasswordHasher HASHER = new PBKDF2();
	
}
