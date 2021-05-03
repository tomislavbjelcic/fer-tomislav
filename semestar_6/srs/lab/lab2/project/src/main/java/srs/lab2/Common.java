package srs.lab2;

import java.nio.file.Path;

import srs.lab2.pw.PBKDF2;
import srs.lab2.pw.PasswordHasher;
import srs.lab2.vault.VaultLoader;

class Common {
	
	static final Path VAULT_PATH = Path.of("./data/vault.txt");
	static final VaultLoader LOADER = new VaultLoader();
	static final PasswordHasher HASHER = new PBKDF2();
	
}
