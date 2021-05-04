package srs.lab2.command;

import static srs.lab2.command.VaultCommandResult.fail;
import static srs.lab2.command.VaultCommandResult.success;

import java.util.Arrays;

import srs.lab2.pw.PasswordUtils;
import srs.lab2.vault.Vault;

public class PasswordChangeCommand extends AbstractVaultCommand {

	@Override
	protected VaultCommandResult execute(Vault vault, String username) {
		var uinfo = vault.getUserInfo(username);
		if (uinfo == null) {
			return fail("User \"" + username + "\" does not exist.");
		}
		
		char[] pw = PasswordUtils.getPasswordFromConsole("Password: ");
		char[] repeat = PasswordUtils.getPasswordFromConsole("Repeat Password: ");
		boolean eq = Arrays.equals(pw, repeat);
		if (!eq)
			return fail("Password change failed. Password mismatch.");
		
		vault.putUser(username, pw);
		return success("Password change successful.", true);
	}

}
