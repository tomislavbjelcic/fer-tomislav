package srs.lab2.command;

import srs.lab2.vault.Vault;

public interface VaultCommand {
	
	VaultCommandResult execute(Vault vault, String[] args);
	
}
