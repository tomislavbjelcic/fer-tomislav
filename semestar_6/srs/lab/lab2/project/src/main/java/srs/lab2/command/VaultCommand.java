package srs.lab2.command;

import srs.lab2.vault.Vault;

/**
 * Predstavlja naredbu koja se provodi nad spremnikom {@link Vault}
 * 
 * @author tomislav
 *
 */
public interface VaultCommand {
	
	/**
	 * Izvršava naredbu nad spremnikom {@code vault} i pri tome prima argumente {@code args}.
	 * 
	 * @param vault
	 * @param args
	 * @return rezultat naredbe kao instanca objekta {@link VaultCommandResult}.
	 */
	VaultCommandResult execute(Vault vault, String[] args);
	
}
