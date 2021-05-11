package srs.lab2.command;

import static srs.lab2.command.VaultCommandResult.fail;
import static srs.lab2.command.VaultCommandResult.success;

import srs.lab2.vault.Vault;

public class ForcePasswordCommand extends AbstractVaultCommand {

	@Override
	protected VaultCommandResult execute(Vault vault, String username) {
		var uinfo = vault.getUserInfo(username);
		if (uinfo == null) {
			return fail("User \"" + username + "\" does not exist.");
		}
		
		vault.forcePassword(username);
		return success("User will be requested to change password on next login.", true);
	}

}
