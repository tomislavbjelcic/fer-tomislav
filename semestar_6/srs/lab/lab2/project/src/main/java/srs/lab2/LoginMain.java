package srs.lab2;

import static srs.lab2.Common.HASHER;
import static srs.lab2.Common.LOADER;
import static srs.lab2.Common.VAULT_PATH;

import srs.lab2.command.LoginCommand;
import srs.lab2.command.VaultCommand;
import srs.lab2.command.VaultCommandResult;
import srs.lab2.vault.Vault;

public class LoginMain {
	
	private static final VaultCommand CMD = new LoginCommand();
	
	public static void main(String[] args) {
		Vault vault = LOADER.load(VAULT_PATH, HASHER);
		
		VaultCommandResult result = CMD.execute(vault, args);
		if (result.vaultChanged) {
			LOADER.save(VAULT_PATH, vault);
		}
		
		System.out.println(result);
	}
	
}
