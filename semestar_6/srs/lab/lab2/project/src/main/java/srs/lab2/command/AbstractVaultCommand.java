package srs.lab2.command;

import static srs.lab2.command.VaultCommandResult.fail;

import srs.lab2.vault.Vault;

public abstract class AbstractVaultCommand implements VaultCommand {
	
	protected String checkFor1Arg(String[] args) {
		if (args == null)
			return "Args null.";
		
		if (args.length != 1)
			return "Exactly 1 argument required: username.";
		
		return null;
	}
	
	@Override
	public VaultCommandResult execute(Vault vault, String[] args) {
		String err = checkFor1Arg(args);
		if (err != null)
			return fail(err);
		
		String username = args[0];
		return execute(vault, username);
	}
	
	protected abstract VaultCommandResult execute(Vault vault, String username);
	
	protected VaultCommandResult nonexistentUser(String username) {
		return VaultCommandResult.fail("User \"" + username + "\" does not exist.");
	}
	
}
