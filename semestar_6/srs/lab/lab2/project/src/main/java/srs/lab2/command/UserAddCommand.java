package srs.lab2.command;

import java.util.Arrays;

import srs.lab2.pw.PasswordUtils;
import srs.lab2.vault.Vault;

import static srs.lab2.command.VaultCommandResult.*;

public class UserAddCommand extends AbstractVaultCommand {

	@Override
	protected VaultCommandResult execute(Vault vault, String username) {
		var uinfo = vault.getUserInfo(username);
		if (uinfo != null) {
			return fail("User \"" + username + "\" already exists.");
		}
		
		String userErr = PasswordUtils.checkUsername(username);
		if (userErr != null)
			return fail(userErr);
		
		char[] pw = PasswordUtils.getPasswordFromConsole("Password: ");
		char[] repeat = PasswordUtils.getPasswordFromConsole("Repeat Password: ");
		boolean eq = Arrays.equals(pw, repeat);
		if (!eq)
			return fail("User add failed. Password mismatch.");
		String err = PasswordUtils.checkPassword(pw);
		if (err != null)
			return fail(err);
		
		vault.putUser(username, pw);
		
		return success("User add successfully added.", true);
	}

	

}
